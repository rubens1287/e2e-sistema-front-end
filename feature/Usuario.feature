#language:pt

Funcionalidade: Usuario
  Eu como usuario gostaria de criar meu proprio login para aplicacao

  Cenario: CT001 - Criar novo usuario
    Dado Eu estou na aplicação "http://seubarriga.wcaquino.me"
    Quando Eu informar o nome, email e senha
      | Nome        | Email               | Senha |
      | Rubens Lobo | rubens.lobo@gft.com | 1234  |
    Entao sera apresentado a mensagem "Usuário inserido com sucesso"


   Cenario: CT002 - Executa login invalido
     Dado Eu estou na aplicação "https://www.pernambucanas.com.br/customer/account/login/referer/aHR0cHM6Ly93d3cucGVybmFtYnVjYW5hcy5jb20uYnIv/"
     Quando Eu preencher o email "rubens.lobo@gft.com" e senha "123456789" e clicar no botao entrar
     Entao devera ser apresentado a mensagem "Você não digitou os dados corretamente ou a sua conta está temporariamente desativada."



