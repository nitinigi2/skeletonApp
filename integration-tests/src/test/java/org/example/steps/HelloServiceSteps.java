package org.example.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.service.HelloService;

public class HelloServiceSteps {

    private HelloService helloService;
    private String message;

    @Given("I have a HelloService")
    public void i_have_a_HelloService() {
        helloService = new HelloService();
    }

    @When("I request the hello world message")
    public void i_request_the_hello_world_message() {
        message = helloService.getHelloWorldMessage();
    }

    @Then("I should receive {string}")
    public void i_should_receive(String expectedMessage) {
        assertEquals(expectedMessage, message);
    }
}
