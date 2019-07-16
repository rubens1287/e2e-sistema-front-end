package gherkin.stepdefinition;

import br.com.pom.pernambucanas.LoginDoCliente;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class PernambucanasSteps {

    LoginDoCliente cliente = new LoginDoCliente();

    @Quando("Eu preencher o email {string} e senha {string} e clicar no botao entrar")
    public void euPreencherOEmailESenhaEClicarNoBotaoEntrar(String email, String pass) {
        cliente.executaLogin(email,pass);
    }


    @Entao("devera ser apresentado a mensagem {string}")
    public void devera_ser_apresentado_a_mensagem(String msg) {
        cliente.validaMensagemError(msg);

    }
}
