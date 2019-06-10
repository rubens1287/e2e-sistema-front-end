package gherkin.stepdefinition;

import br.com.pom.pennatec.LoginWeb;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class LoginWebSteps {

    LoginWeb loginWeb = new LoginWeb();

    @Given("^I am at the login screen$")
    public void iAmAtTheLoginScreen() throws Throwable {
        loginWeb.loadUrl();
    }

    @When("^I log in with user \"([^\"]*)\" and password \"([^\"]*)\"$")
    public void iLogInWithUserAndPassword(String user, String pass) throws Throwable {
        loginWeb.doLogin(user, pass);
    }

    @Then("^the error message \"([^\"]*)\" will be displayed$")
    public void theErrorMessageWillBeDisplayed(String msg) throws Throwable {
        loginWeb.shouldBeShowMessage(msg);
    }

}
