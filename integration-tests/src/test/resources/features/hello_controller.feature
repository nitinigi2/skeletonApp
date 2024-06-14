Feature: Hello API

  Scenario: Get hello message
    Given the hello service is running
    When the client calls /hello
    Then the response should be "hello world"


  Scenario: Get authenticated message for admin endpoint
    Given the admin secret not added
    When the client calls /admin
    Then the response code should be 401

  Scenario: Get authenticated message for user endpoint
    Given the user secret not added
    When the client calls /user
    Then the response code should be 401