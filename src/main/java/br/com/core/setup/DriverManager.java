package br.com.core.setup;

import br.com.core.datadriven.Excel;
import cucumber.api.Scenario;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.winium.WiniumDriver;
import org.openqa.selenium.winium.WiniumDriverService;

public abstract class DriverManager {

    public static ThreadLocal<Scenario> testScenario = new ThreadLocal<Scenario>();
    private static ThreadLocal<WebDriver> browser = new ThreadLocal<>();
    private static ThreadLocal<WindowsDriver> windowsDriver = new ThreadLocal<>();
    private static WiniumDriver winiumDriver;
    private static WiniumDriverService winiumService;
    private static ThreadLocal<AndroidDriver>  androidDriver = new ThreadLocal<>();
    private static ThreadLocal<AppiumDriverLocalService> service = new ThreadLocal<>();
    private static Process process;
    //protected static Scenario testScenario;

    public  static AppiumDriverLocalService getService() {
        return service.get();
    }

    public  static void setService(AppiumDriverLocalService service) {
        DriverManager.service.set(service);
    }

    public static Process getProcess() {return process;}

    public static void setProcess(Process process) {DriverManager.process = process;}

    public static WebDriver getBrowser() {
        return browser.get();
    }

    public static void setBrowser(WebDriver browser) {DriverManager.browser.set(browser); }

    public static WindowsDriver getWindowsDriver() {
        return windowsDriver.get();
    }

    public static void setWindowsDriver(WindowsDriver desktop) {
        DriverManager.windowsDriver.set(desktop);
    }

    public WiniumDriverService getWiniumService() {
        return winiumService;
    }

    public void setWiniumService(WiniumDriverService winiumService) {
        this.winiumService = winiumService;
    }

    public static WiniumDriver getWiniumDriver() { return winiumDriver; }

    public static void setWiniumDriver(WiniumDriver winiumDriver) { DriverManager.winiumDriver = winiumDriver; }

    public  static AndroidDriver getAndroidDriver() {
        return androidDriver.get();
    }

    public  static void setAndroidDriver(AndroidDriver androidDriver)
    {
        DriverManager.androidDriver.set(androidDriver);
    }

}
