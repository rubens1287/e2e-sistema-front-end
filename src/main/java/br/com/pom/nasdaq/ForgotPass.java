package br.com.pom.nasdaq;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.core.view.Action;
import org.openqa.selenium.By;

public class ForgotPass extends DriverManager {

    private By txtForgotPass = By.id("ctl00_two_column_main_content_EmailAddress");
    private By btnForgotPass = By.id("ctl00_two_column_main_content_ForgotPassword");

    public void sendingEmailToRescuePassword(String email){
        Verifications.verifyElementIsClickable(getBrowser(),txtForgotPass,1);
        Action.setText(getBrowser(),txtForgotPass,email,30);
        ExtentReports.appendToReport(getBrowser());
        Action.clickOnElement(getBrowser(),btnForgotPass,30);
    }
}
