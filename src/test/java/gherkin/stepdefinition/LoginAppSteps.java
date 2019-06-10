package gherkin.stepdefinition;


import br.com.core.setup.AppAndroid;
import br.com.core.setup.DriverManager;
import br.com.pom.metlife.LoginApp;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.remote.DesiredCapabilities;


public class LoginAppSteps extends DriverManager {

    private LoginApp loginApp;

    @Given("^I am at metlife login screen$")
    public synchronized void iAmAtMetlifeLoginScreen() throws Throwable {
         AppAndroid app = new AppAndroid();
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("device", "Google Pixel");
        caps.setCapability("os_version", "7.1");
        caps.setCapability("app", "bs://dfde0754e60179ea442a46ac4a35c5ab6f412398");
        caps.setCapability("name", testScenario.get().getName());
        app.setCapabilities(caps);
        app.setUpDriver(app);
    }

    @When("^I login with user \"([^\"]*)\" and password \"([^\"]*)\" in app$")
    public synchronized void iLoginWithUserAndPasswordInApp(String user, String pass) throws Throwable {
        loginApp = new LoginApp(getAndroidDriver());
        loginApp.doLogin(user, pass);
    }

    @Then("^the error message \"([^\"]*)\" is displayed in app$")
    public synchronized void theErrorMessageIsDisplayedInApp(String msg) throws Throwable {
        loginApp.shouldBeShowMessage(msg);
    }
}
