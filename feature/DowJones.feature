Feature: DowJones
  Brief description of the feature
  I as an <user> must be capable of doing something


   @Parallel @Dowjones
  Scenario: TC001 - Execute login with an invalid user and password
    Given I am at the "https://www.dowjones.com/" login page
    When I login with user "teste@gmail.com" and password "1234" on dowJones
    Then The error message "The login details entered are incorrect. Please try again or contact Customer Service for further assistance." will be displayed