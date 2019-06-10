package br.com.core.setup;

import br.com.core.properties.PropertiesManager;
import br.com.core.report.ExtentReports;
import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;
import java.io.File;
import java.net.URL;

public class AppWinium extends Drivers{

    private WiniumDriverService service;
    private DesktopOptions options;
    private WiniumDriver driver;
    private Process process;
    private String app;

    public AppWinium() {
        super.setupProperties = new PropertiesManager("Setup.properties");
        this.setApp(setupProperties.getProps().getProperty("App.Winium"));
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public WiniumDriver getDriver() {
        return driver;
    }

    public WiniumDriverService getService() {
        return service;
    }

    public void setService(WiniumDriverService service) {
        this.service = service;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public void setUpDriver(Drivers driver) {
        try {
            options = new DesktopOptions();
            options.setApplicationPath(this.getApp());

            if(setupProperties.getProps().getProperty("Host.Winium.Server").isEmpty()){
                service = new WiniumDriverService.Builder()
                        .usingDriverExecutable(new File("src/test/resources/desktop/winium/Winium.Desktop.Driver.exe"))
                        .usingPort(9999).withVerbose(true)
                        .withSilent(false)
                        .buildDesktopService();

                service.start();
                this.driver = new WiniumDriver(service, options);
            }else{
               this.driver = new WiniumDriver(new URL(setupProperties.getProps().getProperty("Host.Winium.Server")+":"+setupProperties.getProps().getProperty("Host.Winium.port")), options);
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            DriverManager.setWiniumDriver(this.driver);
        }
    }

}
