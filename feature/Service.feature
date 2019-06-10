@Parallel
Feature: Service - back-end
  Assists when working with API calls in back-end

  @RestAssured
  Scenario: TC001 - Execute POST on service
    When I send a Post request to API "https://jsonplaceholder.typicode.com/posts"
    """
    {
      title: "Teste",
      body: "teste-body",
      userId: 6
    }
    """
    Then the HTTP Status 201 will be returned

  @RestAssured @Rubens
  Scenario: TC002 - Execute GET on service
    When I send a Get request to API "https://jsonplaceholder.typicode.com/todos/" with code 1
    Then the HTTP Status 200 will be returned with some description data of a book


  @RestAssured
  Scenario: TC003 - Execute PUT on service
    When I send a Put request to API "https://jsonplaceholder.typicode.com/posts/2"
    """
    {
      id: 2,
      title: 'foo',
      body: 'bar',
      userId: 2
    }
    """
    Then the HTTP Status 200 will be returned

  @RestAssured
  Scenario: TC004 - Execute DELETE on service
    When I send a Delete request to API "https://jsonplaceholder.typicode.com/posts/2"
    Then the HTTP Status 200 will be returned