package br.com.pom.calculator;

import br.com.core.setup.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static br.com.core.report.ExtentReports.appendToReport;
import static br.com.core.view.Action.clickOnElement;
import static org.testng.Assert.assertTrue;

public class Standard extends DriverManager {


    public void setStandardCalculator() {
        getWindowsDriver().findElementByAccessibilityId("TogglePaneButton").click();
        getWindowsDriver().findElementByAccessibilityId("Standard").click();
        getWindowsDriver().findElementByAccessibilityId("TogglePaneButton").click();
        String tipoCalculadora = getWindowsDriver().findElementByAccessibilityId("Header").getText();
        assertTrue(tipoCalculadora.contains("Padrão"));
        appendToReport(getWindowsDriver());
    }

    public void executeMath(WebDriver driver, String valorUm, String sinal, String valorDois) {
        clickOnElement(getWindowsDriver(), By.name(valorUm), 10);
        getWindowsDriver().findElementByAccessibilityId(sinal).click();
        clickOnElement(getWindowsDriver(), By.name(valorDois), 10);
        appendToReport(getWindowsDriver());
    }

    public void verifyResult(String resultadoEsperado) {
        getWindowsDriver().findElementByAccessibilityId("equalButton").click();
        String resultadoAtual = getWindowsDriver().findElementByAccessibilityId("CalculatorResults").getText();
        assertTrue(resultadoAtual.contains(resultadoEsperado));
        appendToReport(getWindowsDriver());
    }

}
