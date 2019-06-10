package gherkin.hook;


import br.com.core.integration.OpenStf;
import br.com.core.report.ExtentReports;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static br.com.core.setup.Drivers.closeDriver;

public class Hook extends ExtentReports {

    OpenStf openStf = new OpenStf();

    @Before
    public void init(Scenario scenario) {
        testScenario.set(scenario);

//        openStf.connectToRemoteDevice("c2e7e45");
//        openStf.commandRemoteADB("adb connect");


        /*WinAppDriver desktop driver start example
        AppWindows app = new AppWindows();
        app.setUpDriver(app);*/

        /*Winium desktop driver start example
        AppWinium app2 = new AppWinium();
        app2.setUpDriver(app2);*/

        /*Web Browser driver start example
        AppWeb app1 = new AppWeb();
        app1.setBrowserName("firefox");
        app1.setUpDriver(app1);*/

        /*Android driver start example*/
       /* AppAndroid app = new AppAndroid();
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("device", "Google Pixel");
        caps.setCapability("os_version", "7.1");
        caps.setCapability("app", "bs://dfde0754e60179ea442a46ac4a35c5ab6f412398");
        caps.setCapability("name", scenario.getName());
        app.setCapabilities(caps);
        app.setUpDriver(app);*/

        /*AppWeb app = new AppWeb();
        app.setTestName(scenario.getName() +" - "+ System.getProperty("browser"));
        app.setUpDriver(app);*/
    }

    @After
    public void cleanUp() {

        closeDriver(getAndroidDriver());
        closeDriver(getBrowser());
        closeDriver(getWindowsDriver());

//        openStf.commandRemoteADB("adb disconnect");
//        openStf.disconnectToRemoteDevice();

    }
}
