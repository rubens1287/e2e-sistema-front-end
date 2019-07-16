package br.com.pom.seubarriga;

import br.com.core.asserts.Verifications;
import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.core.view.Action;
import br.com.pom.Constantes;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import java.util.regex.Pattern;

import java.text.Normalizer;
import java.util.Locale;

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


        Faker faker = new Faker(new Locale("pt-BR"));

        getBrowser().findElement(txtNome).sendKeys(nome);
        getBrowser().findElement(txtEmail).sendKeys(unaccent(faker.name().username())+"@gft.com");
        getBrowser().findElement(txtSenha).sendKeys(senha);
        ExtentReports.appendToReport(getBrowser());
        getBrowser().findElement(btnCadastra).click();
    }

    public void validaMensagem(String msg){
        Verifications.verifyTextsExistingElement(getBrowser(),lblMensagem,msg,timeOut);
        ExtentReports.appendToReport(getBrowser());

    }

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
