Feature: Mobile
  Brief description of the feature
  I as a <user> must be able to do something

  @DemoWebMobile @DemoMobile
  Scenario: TC001 - Execute Invalid Login - WEB
    Given I am at the login screen
    When I log in with user "rubens" and password "e1234"
    Then the error message "Erro ao validar Usuario" will be displayed

  @DemoApkMobile @DemoMobile
  Scenario: TC002 - Execute Invalid Login - APP
    Given I am at metlife login screen
    When I login with user "123" and password "123" in app
    Then the error message "Login e/ou senha inválida, por favor, verifique e tente novamente" is displayed in app
