package gherkin.hook;


import br.com.core.report.ExtentReports;
import br.com.core.setup.AppWeb;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import static br.com.core.setup.Drivers.closeDriver;

public class Hook extends ExtentReports {

    @Before
    public void init(Scenario scenario) {
        testScenario.set(scenario);

        AppWeb web = new AppWeb();
        web.setTestName(testScenario.get().getName());
        web.setBrowserName("chrome");
        web.setUpDriver(web);

    }

    @After
    public void cleanUp() {
        closeDriver(getBrowser());

    }
}
