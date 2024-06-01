Feature: Hello Service

  Scenario: Get Hello World Message
    Given I have a HelloService
    When I request the hello world message
    Then I should receive "hello world"
