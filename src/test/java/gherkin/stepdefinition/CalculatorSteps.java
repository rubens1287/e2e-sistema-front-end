package gherkin.stepdefinition;

import br.com.core.asserts.Verifications;
import br.com.core.setup.AppWindows;
import br.com.core.setup.AppWinium;
import br.com.core.setup.DriverManager;
import br.com.pom.calculator.Standard;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static br.com.core.report.ExtentReports.appendToReport;
import static br.com.core.view.Action.clickOnElement;

public class CalculatorSteps extends DriverManager {

    private Standard padrao = new Standard();

    @Given("^I am on calculator in mode standard$")
    public void iAmOnCalculatorInModeStandard() throws Throwable {
        AppWindows app = new AppWindows();
        app.setUpDriver(app);
        padrao.setStandardCalculator();
    }

    @When("^I click on button \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iClickOnButtonAndAnd(String numberOne, String operation, String numberTwo) throws Throwable {
        padrao.executeMath(getWindowsDriver(), numberOne,operation,numberTwo);
    }

    @Then("^should be showed the value \"([^\"]*)\"$")
    public void shouldBeShowedTheValue(String value) throws Throwable {
        padrao.verifyResult(value);
    }


    @Given("^I click on value \"([^\"]*)\" and \"([^\"]*)\" and \"([^\"]*)\" using winium$")
    public void iClickOnValueAndAndUsingWinium(String numberOne, String operation, String numberTwo) throws Throwable {
        getWiniumDriver().findElement(By.id("num"+numberOne+"Button")).click();
        clickOnElement(getWiniumDriver(), By.id("plusButton"),10);
        clickOnElement(getWiniumDriver(), By.id("num"+numberTwo+"Button"),10);
        appendToReport(getWiniumDriver());

    }

    @Then("^should be showed the value \"([^\"]*)\" using winium$")
    public void shouldBeShowedTheValueUsingWinium(String expectedResult) throws Throwable {
        clickOnElement(getWiniumDriver(), By.name("Igual a"),10);
        Verifications.verifyElementExists(getWiniumDriver(),By.name("Exibição é "+expectedResult),10);
    }

    @Given("^I am on calculator using winium$")
    public void iAmOnCalculatorUsingWinium() throws Throwable {
        AppWinium appWinium =  new AppWinium();
        appWinium.setUpDriver(appWinium);

    }
}
