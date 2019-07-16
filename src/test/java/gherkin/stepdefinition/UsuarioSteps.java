package gherkin.stepdefinition;

import br.com.core.report.ExtentReports;

import br.com.pom.seubarriga.Usuario;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

import java.util.List;

public class UsuarioSteps {

    private Usuario usuario = new Usuario();


    @Dado("Eu estou na aplicação {string}")
    public void euEstouNaAplicação(String url) {
        usuario.acessaApp(url);
    }

    @Quando("Eu informar o nome, email e senha")
    public void eu_informar_o_nome_email_e_senha(List<List<String>> dataTable) {
        usuario.novoCadastro();
        usuario.preencherCadastro(dataTable.get(1).get(0),dataTable.get(1).get(1),dataTable.get(1).get(2));
    }

    @Entao("sera apresentado a mensagem {string}")
    public void sera_apresentado_a_mensagem(String msg) {
        usuario.validaMensagem(msg);
    }



}
