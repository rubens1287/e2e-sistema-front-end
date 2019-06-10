Feature: Nasdaq
  Brief description of the feature
  I as an <user> must be capable of doing something

  @Nasdaq @Parallel
  Scenario: TC001 - Execute login with an invalid user and password
    Given I am at the "https://community.nasdaq.com/login.aspx?turl=https%3A%2F%2Fwww.nasdaq.com%2F" login page
    When I login with user "12345" and password "1234"
    Then The error message "Username or password not found." will be displayed

  @Nasdaq @Parallel
  Scenario: TC002 - Execute rescue from an account with an invalid email
    Given I am at the "https://community.nasdaq.com/login.aspx?turl=https%3A%2F%2Fwww.nasdaq.com%2F" login page
    When I click at forgot password
    And I enter the invalid email "teste@teste.com.br"
    Then The error message "Email address not found" will be displayed