package br.com.core.setup;

import br.com.core.properties.PropertiesManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.annotation.Nullable;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static br.com.core.setup.AppiumService.getRandomIntegerBetweenRange;

/**
 * Manage Android Driver
 */
public class AppAndroid extends Drivers {

    private static final String OSLINUX = "linux";
    private static final boolean NATIVE_WEB_SCREENSHOT = true;
    private AndroidDriver<WebElement> driver;
    private String deviceName;
    private String avd;
    private File apkFile;
    private boolean fullReset;
    private int avdReadyTimeout;
    private String browser;
    private String appPackage;
    private String appMainActivity;
    private DesiredCapabilities capabilities;
    private String urlSeleniumGrid;

    /**
     * Constructor receive data from Setup.properties
     */
    public AppAndroid() {
        super.setupProperties = new PropertiesManager("Setup.properties");
        setAvd(setupProperties.getProps().getProperty("avd"));
        setDeviceName(setupProperties.getProps().getProperty("deviceName"));
        setApkFile(new File(setupProperties.getProps().getProperty("apkFile")));
        setFullReset(Boolean.parseBoolean(setupProperties.getProps().getProperty("fullReset")));
        setAvdReadyTimeout(Integer.parseInt(setupProperties.getProps().getProperty("avdReadyTimeout")));
        setBrowser(setupProperties.getProps().getProperty("mobileBrowser"));
        setUrlSeleniumGrid(setupProperties.getProps().getProperty("url.selenium.grid"));
    }

    @Override
    public void setUpDriver(Drivers driver) {
        startDriver();
    }

    /**
     * Starts the driver using either the emulator or real device, using configuration from Setup.properties file
     */
    public void startDriver() {
        AppiumService appiumService  = new AppiumService();
        appiumService.getAppiumSessionForAndroid();
        if(avd != null && !avd.isEmpty()){
            startAndroidApp(getAvd(), getApkFile());
        }else if(getApkFile().toString().equals("") && getCapabilities() == null){
            startAndroidApp();

        }else if(getCapabilities() != null){
            startAndroidApp(getCapabilities());

        }else if(!getApkFile().toString().isEmpty()){
            startAndroidApp(getApkFile());
        }

    }

    private DesiredCapabilities getAppropriateCapabilities() {
        DesiredCapabilities capabilities;
        if (DriverManager.getService() != null) {
            capabilities = getDefaultCapabilities();
        } else {
            capabilities = getParallelCapabilities();
        }
        return capabilities;
    }

    private void openDriver(Capabilities capabilities) {
        try {
            if (DriverManager.getService() != null) {
                this.driver = new AndroidDriver<>(getServiceUrl(), capabilities);
            } else {
                this.driver = new AndroidDriver<>(new URL(urlSeleniumGrid), capabilities);
            }
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } finally {
            DriverManager.setAndroidDriver(this.driver);
        }
    }

    /**
     * Add capabilities from Setup.properties and start the driver
     *
     * @param capabilities set capabilities to the drive
     */
    public void startAndroidApp(DesiredCapabilities capabilities) {
        openDriver(capabilities);
    }

    /**
     * Add capabilities from Setup.properties file and start the driver
     *
     * @param avd     emulator name
     * @param apkFile file path from the apk
     */
    public void startAndroidApp(@NotNull String avd, @Nullable File apkFile) {
        DesiredCapabilities capabilities = getAppropriateCapabilities();
        capabilities.setCapability("app", apkFile.getAbsolutePath());
        capabilities.setCapability("avd", avd);
        openDriver(capabilities);
    }

    /**
     * Add capabilities from Setup.properties file and start the driver
     *
     * @param apkFile file path from the apk
     */
    public void startAndroidApp(@Nullable File apkFile) {
        DesiredCapabilities capabilities = getAppropriateCapabilities();
        capabilities.setCapability("app", apkFile.getAbsolutePath());
        openDriver(capabilities);
    }

    /**
     * Add capabilities from Setup.properties file and start the driver
     */
    public void startAndroidApp() {
        DesiredCapabilities capabilities = getAppropriateCapabilities();
        if (getAppPackage() != null && getAppMainActivity() != null) {
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getAppMainActivity());
        } else {
            capabilities.setCapability("browserName", "Chrome");
        }
        openDriver(capabilities);
    }


    /**
     * Get default capabilities for Android
     *
     * @return default capabilities from the framework
     */
    private DesiredCapabilities getDefaultCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("fullReset", isFullReset());
        capabilities.setCapability("chromedriverExecutableDir", getChromeDriverExecutable());
        capabilities.setCapability("deviceName", deviceName);
        ;
        capabilities.setCapability("udid", deviceName);
        capabilities.setCapability("nativeWebScreenshot", NATIVE_WEB_SCREENSHOT);
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("databaseEnabled", true);
        capabilities.setCapability("webStorageEnabled", true);
        capabilities.setCapability("locationContextEnabled", true);
        return capabilities;
    }

    /**
     * Get default Android capabilities for parallel testing
     *
     * @return default capatibilities from the framework
     */
    private DesiredCapabilities getParallelCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", deviceName);
        capabilities.setCapability("udid", deviceName);
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("noReset", "false");
        capabilities.setCapability("automationName", "UIAutomator2");
        capabilities.setCapability("systemPort", String.valueOf((int) getRandomIntegerBetweenRange(8002, 8299)));
        capabilities.setCapability("fullReset", isFullReset());
        capabilities.setCapability("nativeWebScreenshot", true);
        return capabilities;
    }


    /**
     * Gets the path where 'chromedriver' drivers are located, according with the OS of the machine.
     * For compatibility with multiple devices
     *
     * @return path to the driver, according with the OS of the machine
     */
    @NotNull
    private String getChromeDriverExecutable() {
        String so = System.getProperty("os.name").toLowerCase();
        if (so.contains(OSLINUX)) {
            return "src/test/resources/mobile/linux";
        } else {
            return "src/test/resources/mobile/windows";
        }
    }

    public AndroidDriver<WebElement> getDriver() {
        return driver;
    }

    public void setDriver(AndroidDriver<WebElement> driver) {
        this.driver = driver;
    }

    /**
     * Deprecated due to typo. Use getAppPackage instead
     * @return appPackage
     */
    public String getAppPackage() {
        return appPackage;
    }

    /**
     * Deprecated due to typo. Use setAppPackage instead
     */

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppMainActivity() {
        return appMainActivity;
    }

    public void setAppMainActivity(String appMainActivity) {
        this.appMainActivity = appMainActivity;
    }

    public URL getServiceUrl() {
        return DriverManager.getService().getUrl();
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAvd() {
        return avd;
    }

    public void setAvd(String avd) {
        this.avd = avd;
    }

    public File getApkFile() {
        if (apkFile == null) {
            return null;
        }
        return apkFile;
    }

    public void setApkFile(File apkFile) {
        if (apkFile == null) {
            this.apkFile = null;
        } else {
            this.apkFile = apkFile;
        }
    }

    public boolean isFullReset() {
        return fullReset;
    }

    public void setFullReset(boolean fullReset) {
        this.fullReset = fullReset;
    }

    public int getAvdReadyTimeout() {
        return avdReadyTimeout;
    }

    public void setAvdReadyTimeout(int avdReadyTimeout) {
        this.avdReadyTimeout = avdReadyTimeout;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(DesiredCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    public String getUrlSeleniumGrid() {
        return urlSeleniumGrid;
    }

    public void setUrlSeleniumGrid(String urlSeleniumGrid) {
        this.urlSeleniumGrid = urlSeleniumGrid;
    }

}
