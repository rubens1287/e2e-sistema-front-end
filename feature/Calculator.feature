Feature: Calculadora
  Brief description of the feature
  I as a <user> must be able to do something

  @WinAppDriver
  Scenario: CT001 - Execute addition with two values - WinAppDriver
    Given I am on calculator in mode standard
    When I click on button "Quatro" and "plusButton" and "Nove"
    Then  should be showed the value "13"


  @WiniumDriver
  Scenario: CT002 - Executar Soma - WiniumDriver
    Given I am on calculator using winium
    Given I click on value "4" and "Mais" and "9" using winium
    Then  should be showed the value "13" using winium


