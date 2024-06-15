Feature: Hello API

  Scenario: Get hello message
    Given the hello service is running
    When the client calls /public
    Then the response code should be 200
    Then the response should be "hello world"


  Scenario: Get unauthenticated message for secured endpoint
    Given secrets not passed
    When the client calls /secured
    Then the response code should be 401

  Scenario: Get success message for secured endpoint with invalid token
    When the client calls /secured for unauthenticated request with invalid token
    Then the response code should be 401

  Scenario: Get success message for secured endpoint with valid token
    When the client calls /secured for authenticated request
    Then the response code should be 200
    Then the response should be "Hello from secured endpoint"