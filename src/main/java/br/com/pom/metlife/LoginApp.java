package br.com.pom.metlife;

import br.com.core.asserts.Verifications;
import br.com.core.setup.DriverManager;
import br.com.core.report.ExtentReports;
import br.com.core.view.Action;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginApp extends DriverManager {
    private By txtUser = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText[1]");
    private By txtPass = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.EditText[2]");
    private By btnEntrar = By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[1]/android.view.View[2]/android.view.View/android.view.View[2]/android.view.View/android.widget.Button[2]");
    private By lblMessage = By.xpath("//android.view.View[3]");
    private AndroidDriver driver;

    public LoginApp(AndroidDriver driver) {
        this.driver = driver;
    }

    public void loadUrl (){
        Verifications.verifyElementIsClickable(this.driver,txtUser,40);
        ExtentReports.appendToReport(driver,"App open");


    }
    public void doLogin ( String user, String pass) {
        Action.setText(driver,txtUser,user,30);
        Action.setText(driver,txtPass,pass,30);
        //this.driver.hideKeyboard();
        Action.clickOnElement(driver,btnEntrar,10);
        ExtentReports.appendToReport(driver,"Log into the application");
    }

    public void shouldBeShowMessage(String msg){
        Verifications.wait(2);
        new WebDriverWait(driver,20).until(ExpectedConditions.textToBePresentInElement((AndroidElement) getAndroidDriver().findElements(lblMessage).get(1),msg));
        ExtentReports.appendToReport(driver,"Validate message");
    }


}
