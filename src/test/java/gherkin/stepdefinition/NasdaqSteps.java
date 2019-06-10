package gherkin.stepdefinition;

import br.com.core.setup.AppWeb;
import br.com.core.setup.DriverManager;
import br.com.pom.enviroment.EnviromentTaas;
import br.com.pom.nasdaq.ForgotPass;
import br.com.pom.nasdaq.Login;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NasdaqSteps extends DriverManager implements EnviromentTaas {

    private ForgotPass forgotPass = new ForgotPass();
    private Login login = new Login();

    @Given("I am at the {string} login page")
    public void iAmAtTheLoginPage(String url) {

        AppWeb app = new AppWeb();
        app.setTestName(testScenario.get().getName() +" - "+ System.getProperty("browser"));
        //app.setUrlSeleniumGrid(zalenium);
        app.setUpDriver(app);
        login.accessLoginPage(url);
    }

    @When("^I login with user \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iLoginWithUserAndPassword(String user, String pass)  {
        login.executeLogin(getBrowser(), user, pass);
    }

    @Then("^The error message \"([^\"]*)\" will be displayed$")
    public void theErrorMessageWillBeDisplayed(String message)  {
        login.verifyErrorMsg(getBrowser(), message);
    }

    @When("^I click at forgot password$")
    public void iClickAtForgotPassword()  {
        login.clickOnLinkForgotPassword();
    }

    @And("^I enter the invalid email \"([^\"]*)\"$")
    public void iEnterTheInvalidEmail(String email)  {
        forgotPass.sendingEmailToRescuePassword(email);


    }

}
