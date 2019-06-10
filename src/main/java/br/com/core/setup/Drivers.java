package br.com.core.setup;


import br.com.core.properties.PropertiesManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import static br.com.core.report.ExtentReports.logReport;
import static br.com.core.report.ExtentReports.logReportAllDrivers;

public abstract class Drivers {

    protected PropertiesManager setupProperties;

    /**
     * Loads the application on the WebApp
     *
     * @param url set url to load application on the browser
     */
    public static void loadApplication(@NotNull WebDriver driver, String url) {
        PropertiesManager setupProperties = new PropertiesManager("Setup.properties");
        driver.get(url);
        waitPageLoad(driver, setupProperties.getProps().getProperty("Implicit.Wait"));
    }

    /**
     * Deletes the cookies from the browser
     *
     * @author Rubens Lobo
     */
    public static void deleteCookies(@NotNull WebDriver driver) {
        driver.manage().deleteAllCookies();
    }

    /**
     * Sets the default timeout in seconds for the page to load on WebApp
     *
     * @param seconds amount of seconds to wait for the page load in WebApp
     * @author Rubens Lobo
     */
    private static void waitPageLoad(@NotNull WebDriver driver, String seconds) {
        driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(seconds), TimeUnit.SECONDS);
    }

    /**
     * Close driver
     *
     * @author Rubens Lobo
     */
    public static synchronized  void closeDriver(WebDriver driver) {

        if (driver != null) {
            logReport(driver);
            driver.quit();
        }
    }

    /**
     * Close appium service
     *
     * @author Rubens Lobo
     */
    public static void stopAppiumService(AppiumDriverLocalService service) {
        if (service != null) {
            service.stop();
        }
    }

    public static void killProcess(String process) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("tasklist.exe /fo csv /nh");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if (!line.trim().equals("")) {
                    if (line.substring(1, line.indexOf("\"", 1)).equalsIgnoreCase(process)) {
                        Runtime.getRuntime().exec("taskkill /F /IM " + line.substring(1, line.indexOf("\"", 1)));
                    }
                }
            }
            input.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Install apk during runtime
     *
     * @param driver  - Set androidDriver as parameter
     * @param apkFile - Set File path as parameter
     */
    public static void installApp(AndroidDriver driver, File apkFile) {
        driver.installApp(apkFile.getAbsolutePath());
    }

    public abstract void setUpDriver(Drivers driver);

}

