package br.com.core.setup;

import br.com.core.properties.PropertiesManager;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static io.restassured.RestAssured.given;

public class AppiumService {

    private AppiumDriverLocalService service;
    private AppiumServiceBuilder builder;
    private DesiredCapabilities cap;
    private String urlSeleniumGrid;
    PropertiesManager setupProperties;

    public AppiumService() {

        setupProperties = new PropertiesManager("Setup.properties");
        setUrlSeleniumGrid(setupProperties.getProps().getProperty("url.selenium.grid"));
    }

    public AppiumDriverLocalService getAppiumSessionForWindowsApp(String host, String port) {

        AppiumService appiumServer = new AppiumService();
        if(!appiumServer.checkIfServerIsRunning(port)) {
            cap = new DesiredCapabilities();
            cap.setCapability("noReset", "false");
            builder = new AppiumServiceBuilder();
            builder.withIPAddress(host);
            builder.usingPort(Integer.parseInt(port));
            builder.withCapabilities(cap);
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
        } else {
            System.out.println("Appium Server already running on Port - " + port);
        }
        return service;
    }

    public void getAppiumSessionForAndroid(){
        cap = new DesiredCapabilities();
        builder = new AppiumServiceBuilder();
        try{

            cap.setCapability("systemPort", String.valueOf((int)getRandomIntegerBetweenRange(100,9000)));
            builder.withCapabilities(cap);
            builder.usingAnyFreePort();
            builder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER,String.valueOf((int)getRandomIntegerBetweenRange(100,9000)));
            builder.withArgument(AndroidServerFlag.CHROME_DRIVER_PORT,String.valueOf((int)getRandomIntegerBetweenRange(100,9000)));
            builder.withArgument(GeneralServerFlag.SESSION_OVERRIDE);
            builder.withArgument(GeneralServerFlag.LOG_LEVEL,"error");
            if(urlSeleniumGrid.isEmpty()){
                DriverManager.setService(AppiumDriverLocalService.buildDefaultService());
                DriverManager.getService().start();
            }
        }catch (Exception e){
            System.out.println("[getAppiumSessionForAndroid] Error while trying to create Appium session " + e.getMessage());
        }

    }

    private boolean isAvailable(String host, String port) {
        System.out.println("--------------Testing port " + port);
        Socket s = null;
        if(host == null && port == null){
            return false;
        }
        try {
            s = new Socket(host, Integer.parseInt(port));

            // If the code makes it this far without an exception it means
            // something is using the port and has responded.
            System.out.println("--------------Port " + port + " is not available");
            return false;
        } catch (IOException e) {
            System.out.println("--------------Port " + port + " is available");
            return true;
        } finally {
            if( s != null){
                try {
                    s.close();
                } catch (IOException e) {
                    throw new RuntimeException("You should handle this error." , e);
                }
            }
        }
    }

    private boolean verifySessionAvailable(String host, String port){
        String sessionValue;
        try {
            RequestSpecification httpRequest = given();
            Response response = httpRequest.get("http://"+host+":"+port+"/wd/hub/sessions");
            JsonPath jsonPath = new JsonPath(response.body().prettyPrint());
            sessionValue = jsonPath.getString("value");
            if(sessionValue.equalsIgnoreCase("[]")){
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static double getRandomIntegerBetweenRange(double min, double max){
        return (int)(Math.random()*((max-min)+1))+min;
    }

    public static void stopAppiumServer() {
        Drivers.killProcess("node.exe");
    }

    private boolean checkIfServerIsRunning(String port) {

        boolean isServerRunning = false;
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            serverSocket.close();
        } catch (IOException e) {
            isServerRunning = true;
        } finally {
            serverSocket = null;
        }
        return isServerRunning;
    }


    public String getUrlSeleniumGrid() {
        return urlSeleniumGrid;
    }

    public void setUrlSeleniumGrid(String urlSeleniumGrid) {
        this.urlSeleniumGrid = urlSeleniumGrid;
    }
}
