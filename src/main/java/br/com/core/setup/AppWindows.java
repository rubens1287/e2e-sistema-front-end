package br.com.core.setup;

import br.com.core.properties.PropertiesManager;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppWindows extends Drivers {

    private WindowsDriver driver;
    private String app;
    private String appWorkingDir;
    private String host;
    private int implicitWait;
    private AppiumService appiumService;

    public AppWindows() {
        super.setupProperties = new PropertiesManager("Setup.properties");
        setApp(setupProperties.getProps().getProperty("App"));
        setAppWorkingDir(setupProperties.getProps().getProperty("appWorkingDir"));
        setHost(setupProperties.getProps().getProperty("Host.Server.WinAppDriver"));
        setImplicitWait(Integer.parseInt(setupProperties.getProps().getProperty("Implicit.Wait.WinAppDriver")));
        appiumService = new AppiumService();
    }

    public AppWindows(WindowsDriver driver) {
        this.driver = driver;
        super.setupProperties = new PropertiesManager("Setup.properties");
        setApp(setupProperties.getProps().getProperty("App"));
        setAppWorkingDir(setupProperties.getProps().getProperty("appWorkingDir"));
        setHost(setupProperties.getProps().getProperty("Host.Server.WinAppDriver"));
        setImplicitWait(Integer.parseInt(setupProperties.getProps().getProperty("Implicit.Wait.WinAppDriver")));
        appiumService = new AppiumService();
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppWorkingDir() {
        return appWorkingDir;
    }

    public void setAppWorkingDir(String appWorkingDir) {
        this.appWorkingDir = appWorkingDir;
    }

    /**
     * Deprecated due to typo in name. Use getImplicitName() instead
     */
    public int getImplicitWait() {
        return implicitWait;
    }

    /**
     * Deprecated due to typo in name. Use getImplicitName() instead
     */
    public void setImplicitWait(int implicitWait) {
        this.implicitWait = implicitWait;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public WindowsDriver getDriver() {
        return driver;
    }

    public void setDesktop(WindowsDriver driver) {
        this.driver = driver;
    }

    @Override
    public void setUpDriver(Drivers driver) {
        try {
            String address = getHost().substring(getHost().indexOf('/') + 2);
            String ip = address.substring(0, address.indexOf(':'));
            String port1 = getHost().substring(getHost().lastIndexOf(':') + 1);
            String port = port1.substring(0, port1.indexOf('/'));
            appiumService.getAppiumSessionForWindowsApp(ip, port);
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", getAppWorkingDir() + getApp());
            capabilities.setCapability("appWorkingDir", getAppWorkingDir());
            capabilities.setCapability("deviceName", "WindowsPC");
            capabilities.setCapability("platformName", "Windows");
            this.driver = new WindowsDriver(new URL(getHost()), capabilities);
            this.driver.manage().timeouts().implicitlyWait(getImplicitWait(), TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DriverManager.setWindowsDriver(this.driver);
        }
    }

}
