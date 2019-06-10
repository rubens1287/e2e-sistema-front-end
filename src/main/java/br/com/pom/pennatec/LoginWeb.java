package br.com.pom.pennatec;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.*;
import br.com.core.view.Action;
import org.openqa.selenium.By;

public class LoginWeb extends DriverManager {

    private AppAndroid app = new AppAndroid();
    private By txtUser = By.id("login");
    private By txtPass = By.id("pass");
    private By btnEntrar = By.id("trocar");
    private By lblMessage = By.xpath("//div[@data-animation='pop']//h2");

    public LoginWeb() {
        app.setUpDriver(app);
        setAndroidDriver(app.getDriver());
    }

    public void loadUrl (){
        Drivers.loadApplication(app.getDriver(), "https://www.pennatec.com.br/mobile/login/Login.Layout.php?&cliente=teste&db=1&nome=TESTE");
        Verifications.verifyElementIsClickable(app.getDriver(), txtUser, 20);
        ExtentReports.appendToReport(app.getDriver());
    }


    public void doLogin (String user, String pass) {
        app.getDriver().findElement(txtUser).sendKeys(user);
        Action.setText(app.getDriver(),txtPass,pass,10);
        app.getDriver().hideKeyboard();
        Action.clickOnElement(app.getDriver(), btnEntrar, 20);
        ExtentReports.appendToReport(app.getDriver());
    }

    public void shouldBeShowMessage(String msg){
        //Verifications.verifyTextsElementClickable(app.getDriver(),lblMessage, msg, 20);
        Verifications.wait(1);
        ExtentReports.appendToReport(app.getDriver());
    }
}
