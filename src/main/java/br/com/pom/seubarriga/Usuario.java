package br.com.pom.seubarriga;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.core.view.Action;
import br.com.pom.Constantes;
import org.openqa.selenium.By;

public class Usuario extends DriverManager implements Constantes {

    private By btnNovoUsuario = By.xpath("//a[contains(text(),'Novo usuário?')]");
    private By txtNome = By.name("nome");
    private By txtEmail = By.name("email");
    private By txtSenha = By.name("senha");
    private By btnCadastra = By.xpath("//input[@class='btn btn-primary']");
    private By lblMensagem = By.xpath("//div[@class='alert alert-success']");


    public void acessaApp (String url){
        getBrowser().get(url);
    }


    public void novoCadastro (){
        Action.clickOnElement(getBrowser(),btnNovoUsuario,timeOut);
        Verifications.verifyElementIsClickable(getBrowser(),txtNome,timeOut);
        ExtentReports.appendToReport(getBrowser());
    }

    public void preencherCadastro(String nome, String email, String senha){
        getBrowser().findElement(txtNome).sendKeys(nome);
        getBrowser().findElement(txtEmail).sendKeys(email);
        getBrowser().findElement(txtSenha).sendKeys(senha);
        ExtentReports.appendToReport(getBrowser());
        getBrowser().findElement(btnCadastra).click();
    }

    public void validaMensagem(String msg){
        Verifications.verifyTextsExistingElement(getBrowser(),lblMensagem,msg,timeOut);

    }


}
