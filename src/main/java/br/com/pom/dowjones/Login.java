package br.com.pom.dowjones;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.core.view.Action;
import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckImpl;
import org.openqa.selenium.By;

public class Login extends DriverManager {

    private By lnkSignIn = By.xpath("//a[contains(text(),'Log in')]");
    private By lnkRiskComplice = By.xpath("//header[@class='page-header dropdown-submenu-opened']//li[2]//div[1]//div[1]");
    private By txtEmail = By.id("email");
    private By txtPass = By.id("password");
    private By btnSignIn = By.className("sign-in");

    public void executeLogin(String user, String pass){
        Verifications.verifyElementIsClickable(getBrowser(),lnkSignIn,30);
        Action.clickOnElement(getBrowser(),lnkSignIn,30);
        Verifications.wait(1);
        Action.clickOnElement(getBrowser(),lnkRiskComplice,30);

        Verifications.verifyElementIsClickable(getBrowser(),txtEmail,30);
        Action.setText(getBrowser(),txtEmail, user,30);

        Verifications.verifyElementIsClickable(getBrowser(),txtPass,30);
        Action.setText(getBrowser(),txtPass,pass,30);
        ExtentReports.appendToReport(getBrowser());
        Action.clickOnElement(getBrowser(),btnSignIn,30);
    }
}
