package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class HelloControllerSteps {

    @LocalServerPort
    private int port;

    private Response response;

    @Given("the hello service is running")
    public void theHelloServiceIsRunning() throws InterruptedException {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=port;
        TimeUnit.SECONDS.sleep(5);
        // Setup steps if necessary
    }

    @When("the client calls \\/hello")
    public void theClientCallsHello() {
        response = RestAssured.get("/hello");
    }

    @Then("the response should be {string}")
    public void theResponseShouldBe(String expectedResponse) {
        String actualResponse = response.getBody().asString();
        assertThat(actualResponse, equalTo(expectedResponse));
    }

    @Given("the admin secret not added")
    public void theAdminSecretNotAdded() {
        // do nothing
    }

    @When("the client calls \\/admin")
    public void theClientCallsAdmin() {
        response = RestAssured.get("/admin");
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int arg0) {
        int actualStatusCode = response.getStatusCode();
        assertThat(actualStatusCode, equalTo(arg0));
    }

    @Given("the user secret not added")
    public void theUserSecretNotAdded() {
        // do nothing
    }

    @When("the client calls \\/user")
    public void theClientCallsUser() {
        response = RestAssured.get("/admin");
    }
}
