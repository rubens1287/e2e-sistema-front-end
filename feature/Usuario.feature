#language:pt

Funcionalidade: Usuario
  Eu como usuario gostaria de criar meu proprio login para aplicacao

  Cenario: CT001 - Criar novo usuario
    Dado Eu estou na aplicação "http://seubarriga.wcaquino.me"
    Quando Eu informar o nome, email e senha
      | Nome        | Email               | Senha |
      | Rubens Lobo | rubens.lobo@gft.com | 1234  |
    Entao sera apresentado a mensagem "Usuário inserido com sucesso"


