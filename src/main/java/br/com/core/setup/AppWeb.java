package br.com.core.setup;

import br.com.core.properties.PropertiesManager;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static br.com.core.setup.DriverManager.getBrowser;


public class AppWeb extends Drivers {

    DesiredCapabilities capabilities;
    private WebDriver driver;
    private String os;
    private String pathDownload;
    private String url;
    private String browserName;
    private String testName;
    private String urlSeleniumGrid;


    public AppWeb() {
        super.setupProperties = new PropertiesManager("Setup.properties");
        this.os = System.getProperty("os.name").toLowerCase();
        this.pathDownload = setupProperties.getProps().getProperty("pathDownload");
        this.url = System.getProperty("url");
        this.browserName = System.getProperty("browser");
        this.urlSeleniumGrid = setupProperties.getProps().getProperty("url.selenium.grid");
    }

    public AppWeb(WebDriver driver) {
        this.driver = driver;
        super.setupProperties = new PropertiesManager("Setup.properties");
        this.os = System.getProperty("os.name").toLowerCase();
        this.pathDownload = setupProperties.getProps().getProperty("pathDownload");
        this.url = System.getProperty("url");
        this.browserName = System.getProperty("browser");
        this.urlSeleniumGrid = setupProperties.getProps().getProperty("url.selenium.grid");
    }

    public AppWeb(WebDriver driver, String browserName) {
        this.driver = driver;
        super.setupProperties = new PropertiesManager("Setup.properties");
        this.os = System.getProperty("os.name").toLowerCase();
        this.pathDownload = setupProperties.getProps().getProperty("pathDownload");
        this.url = System.getProperty("url");
        this.browserName = browserName;
        this.urlSeleniumGrid = setupProperties.getProps().getProperty("url.selenium.grid");
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private void setUrl(WebDriver appDriver){
        try {
            if (!url.isEmpty() && url != null) {
                appDriver.get(url);
            }
        }catch (Exception e){}
    }

    private DesiredCapabilities chromeCapabilities(DesiredCapabilities desiredCapabilities) {
        desiredCapabilities = DesiredCapabilities.chrome();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.prompt_for_download", "false");
        chromePrefs.put("plugins.plugins_disabled", new String[]{
                "Adobe Flash Player", "Chrome PDF Viewer"});
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        desiredCapabilities.setCapability("name", getTestName());
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return desiredCapabilities;
    }

    private DesiredCapabilities chromeHeadLessCapabilities(DesiredCapabilities desiredCapabilities) {
        desiredCapabilities = DesiredCapabilities.chrome();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("disable-extensions");
        options.addArguments("--ignore-ssl-errors");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("disable-gpu");
        options.addArguments("headless");
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        desiredCapabilities.setCapability("name", getTestName());
        desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        return desiredCapabilities;
    }

    private DesiredCapabilities internetExplorerCapabilities(DesiredCapabilities desiredCapabilities) {

        desiredCapabilities = DesiredCapabilities.internetExplorer();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
                true);
        desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        desiredCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
        desiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS); //Accept unexpected alerts
        desiredCapabilities.setCapability("requireWindowFocus", true);
        desiredCapabilities.setCapability("enablePersistentHover", false);
        desiredCapabilities.setCapability("name", getTestName());
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        return desiredCapabilities;

    }

    private DesiredCapabilities firefoxCapabilities(DesiredCapabilities desiredCapabilities) {

        desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        desiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS); //Accept unexpected alerts
        desiredCapabilities.setCapability("requireWindowFocus", true);
        desiredCapabilities.setCapability("enablePersistentHover", false);
        desiredCapabilities.setCapability("name", getTestName());
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        return desiredCapabilities;

    }

    private DesiredCapabilities edgeCapabilities(DesiredCapabilities desiredCapabilities) {

        desiredCapabilities = DesiredCapabilities.edge();
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        desiredCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS); //Accept unexpected alerts
        desiredCapabilities.setCapability("requireWindowFocus", true);
        desiredCapabilities.setCapability("enablePersistentHover", false);
        desiredCapabilities.setCapability("name", getTestName());
        desiredCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);

        return desiredCapabilities;
    }

    private WebDriver initChromeDriver() {
        WebDriver appDriver = null;

        if (getBrowser() == null || getBrowser().toString().contains("null")) {

            if (!urlSeleniumGrid.isEmpty()) {
                try {
                    appDriver = new RemoteWebDriver(new URL(urlSeleniumGrid),
                            chromeCapabilities(getCapabilities()));
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                WebDriverManager.chromedriver().setup();
                HashMap<String, Object> chromePrefs = new HashMap<>();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized", "--ignore-ssl-errors","–-no-sandbox","--disable-infobars","ignore-certicate-errors");
                options.addExtensions(new File("./src/test/resources/extensions/PDF-Viewer_v2.0.466.crx"));
                if (pathDownload.isEmpty()) {
                    chromePrefs.put("download.default_directory", System.getProperty("user.dir") + "\\target\\download\\");
                } else {
                    chromePrefs.put("download.default_directory", pathDownload);
                }
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.prompt_for_download", "false");
                options.setExperimentalOption("prefs", chromePrefs);
                appDriver = new ChromeDriver(options);
            }
            appDriver.manage().window().maximize();
            setUrl(appDriver);
        }
        return appDriver;
    }

    private WebDriver initFirefoxDriver() {
        WebDriver appDriver = null;
        if (getBrowser() == null || getBrowser().toString().contains("null")) {
            if (!urlSeleniumGrid.isEmpty()) {
                try {
                    Capabilities capabilities = firefoxCapabilities(getCapabilities());
                    appDriver = new RemoteWebDriver(new URL(urlSeleniumGrid),
                            capabilities);
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions options = new FirefoxOptions();

                if (pathDownload.isEmpty()) {
                    options.addPreference("browser.download.dir", System.getProperty("user.dir") + "\\target\\download\\");
                } else {
                    options.addPreference("browser.download.dir", pathDownload);
                }
                options.addPreference("browser.download.folderList", 2);
                options.addPreference("browser.download.manager.showWhenStarting", false);
                options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
                options.addPreference("browser.helperApps.neverAsk.openFile", "");
                options.addPreference("browser.helperApps.alwaysAsk.force", false);
                options.addPreference("browser.download.manager.alertOnEXEOpen", false);
                options.addPreference("browser.download.manager.focusWhenStarting", false);
                options.addPreference("browser.download.manager.useWindow", false);
                options.addPreference("browser.download.manager.showAlertOnComplete", false);
                options.addPreference("browser.download.manager.closeWhenDone", true);
                options.addPreference("pdfjs.disabled", true);
                appDriver = new FirefoxDriver(options);
            }
            appDriver.manage().window().maximize();
            setUrl(appDriver);
        }
        return appDriver;
    }

    private WebDriver initInternetExplorer() {
        WebDriver appDriver = null;
        if (getBrowser() == null || getBrowser().toString().contains("null")) {
            if (!urlSeleniumGrid.isEmpty()) {
                try {
                    appDriver = new RemoteWebDriver(new URL(urlSeleniumGrid),
                            internetExplorerCapabilities(getCapabilities()));
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                WebDriverManager.iedriver().setup();
                appDriver = new InternetExplorerDriver();
            }
            appDriver.manage().window().maximize();
            setUrl(appDriver);
        }
        return appDriver;
    }

    private WebDriver initHeadLess() {
        WebDriver appDriver = null;
        if (getBrowser() == null || getBrowser().toString().contains("null")) {
            if (!urlSeleniumGrid.isEmpty()) {
                try {
                    appDriver = new RemoteWebDriver(new URL(urlSeleniumGrid),
                            chromeHeadLessCapabilities(getCapabilities()));
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("window-size=1366,768","--disable-dev-shm-usage","--no-sandbox", "disable-extensions", "--ignore-ssl-errors", "disable-gpu", "headless");
                appDriver = new ChromeDriver(options);
            }
            appDriver.manage().window().maximize();
            setUrl(appDriver);
        }
        return appDriver;
    }

    private WebDriver initEdge() {
        WebDriver appDriver = null;

        if (getBrowser() == null || getBrowser().toString().contains("null")) {

            if (!urlSeleniumGrid.isEmpty()) {
                try {
                    appDriver = new RemoteWebDriver(new URL(urlSeleniumGrid),
                            edgeCapabilities(getCapabilities()));
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                WebDriverManager.edgedriver().setup();
                appDriver = new EdgeDriver();
            }
            appDriver.manage().window().maximize();
            setUrl(appDriver);
        }
        return appDriver;
    }

    @Override
    public void setUpDriver(Drivers driver) {

        String path = "./src/test/resources/drivers";
        String proxy = setupProperties.getProps().getProperty("proxy.host") + ":" + setupProperties.getProps().getProperty("proxy.port");
        String proxyUser = setupProperties.getProps().getProperty("proxy.user");
        String proxyPass = setupProperties.getProps().getProperty("proxy.pass");

        try {

            switch (browserName.toUpperCase()) {

                case "CHROME":
                    WebDriverManager.chromedriver().config().setTargetPath(path);
                    if(!proxy.equalsIgnoreCase(":")){
                        WebDriverManager.chromedriver().proxy(proxy);
                        WebDriverManager.chromedriver().proxyUser(proxyUser);
                        WebDriverManager.chromedriver().proxyPass(proxyPass);
                    }
                    this.driver = initChromeDriver();
                    break;
                case "FIREFOX":
                    WebDriverManager.firefoxdriver().config().setTargetPath(path);
                    if (!proxy.equalsIgnoreCase(":")) {
                        WebDriverManager.firefoxdriver().proxy(proxy);
                        WebDriverManager.firefoxdriver().proxyUser(proxyUser);
                        WebDriverManager.firefoxdriver().proxyPass(proxyPass);
                    }
                    this.driver = initFirefoxDriver();
                    break;
                case "IE":
                    WebDriverManager.iedriver().config().setTargetPath(path);
                    if (!proxy.equalsIgnoreCase(":")) {
                        WebDriverManager.iedriver().proxy(proxy);
                        WebDriverManager.iedriver().proxyUser(proxyUser);
                        WebDriverManager.iedriver().proxyPass(proxyPass);
                    }
                    this.driver = initInternetExplorer();
                    break;
                case "HEADLESS":
                    WebDriverManager.chromedriver().config().setTargetPath(path);
                    if (!proxy.equalsIgnoreCase(":")) {
                        WebDriverManager.chromedriver().proxy(proxy);
                        WebDriverManager.chromedriver().proxyUser(proxyUser);
                        WebDriverManager.chromedriver().proxyPass(proxyPass);
                    }
                    this.driver = initHeadLess();
                    break;
                case "EDGE":
                    WebDriverManager.edgedriver().config().setTargetPath(path);
                    if (!proxy.isEmpty()) {
                        WebDriverManager.edgedriver().proxy(proxy);
                        WebDriverManager.edgedriver().proxyUser(proxyUser);
                        WebDriverManager.edgedriver().proxyPass(proxyPass);
                    }
                    this.driver = initEdge();
                    break;
                default:
                    Assert.fail("The browser provided is invalid, please check your browser variable in POM file!");
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        } finally {
            new NgWebDriver((JavascriptExecutor) this.driver);
            DriverManager.setBrowser(this.driver);
        }
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
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


