package br.com.pom.pernambucanas;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.pom.Constantes;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginDoCliente extends DriverManager implements Constantes {

    private By txtEmail = By.id("email");
    private By txtPass = By.id("pass");
    private By btnEntrar = By.name("send");
    private By lblMsg = By.xpath("//div[@data-bind='html: message.text']");


    public void executaLogin(String usuario, String senha){
        ExtentReports.appendToReport(getBrowser());
        getBrowser().findElement(txtEmail).sendKeys(usuario);
        getBrowser().findElement(txtPass).sendKeys(senha);
        getBrowser().findElement(btnEntrar).click();
    }


    public void validaMensagemError(String msg){

        //Verifications.verifyElementExists(getBrowser(),lblMsg,timeOut);
        Verifications.wait(1);
        ExtentReports.appendToReport(getBrowser());
        //new WebDriverWait(getBrowser(),timeOut).until(ExpectedConditions.textToBePresentInElementValue(lblMsg,msg));

    }



}
