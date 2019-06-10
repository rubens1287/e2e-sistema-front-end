package br.com.pom.nasdaq;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.core.view.Action;
import de.retest.recheck.Recheck;
import de.retest.recheck.RecheckImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Login extends DriverManager {

    private By txtUserName = By.id("ctl00_two_column_main_content_NASDAQLogin1_UserName");
    private By txtPass = By.id("ctl00_two_column_main_content_NASDAQLogin1_Password");
    private By btnLogIn = By.id("ctl00_two_column_main_content_NASDAQLogin1_LoginButton");
    private By msgError = By.id("ctl00_two_column_main_content_NASDAQLogin1_lblError");
    private By lnkForgotPass = By.id("ctl00_two_column_main_content_NASDAQLogin1_hlForgot");
    private Recheck re;

    public void accessLoginPage(String url){
        getBrowser().get(url);
        ExtentReports.appendToReport(getBrowser());
    }

    public void executeLogin(WebDriver driver, String user, String pass){
        Verifications.verifyElementIsClickable(driver, txtUserName,30);
        ExtentReports.appendToReport(getBrowser());
        driver.findElement(txtUserName).sendKeys(user);
        driver.findElement(txtPass).sendKeys(pass);
        driver.findElement(btnLogIn).click();
    }

    public void verifyErrorMsg(WebDriver driver, String msg){
        Verifications.verifyElementExists(driver, By.xpath("//*[contains(text(),'"+msg+"')]"),30);
        ExtentReports.appendToReport(getBrowser());
    }

    public void clickOnLinkForgotPassword(){
        ExtentReports.appendToReport(getBrowser());
        Action.clickOnElement(getBrowser(),lnkForgotPass,30);
    }
}
