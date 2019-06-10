package gherkin.stepdefinition;

import br.com.core.setup.AppWeb;
import br.com.pom.dowjones.Login;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class DowJonesSteps {

    private Login login = new Login();

    @When("^I login with user \"([^\"]*)\" and password \"([^\"]*)\" on dowJones$")
    public void iLoginWithUserAndPasswordOnDowJones(String user, String pass) throws Throwable {
        login.executeLogin(user,pass);
    }


}
