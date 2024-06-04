Feature: Hello API

  Scenario: Get hello message
    Given the hello service is running
    When the client calls /hello
    Then the response should be "hello world"