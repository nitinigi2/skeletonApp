package org.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class HelloControllerSteps extends AbstractTestcontainers{

    @LocalServerPort
    private int port;

    private Response response;

    private static final String PUBLIC_URL = "/public";
    private static final String SECURED_URL = "/secured";

    @Given("the hello service is running")
    public void theHelloServiceIsRunning() {
        RestAssured.baseURI="http://localhost";
        RestAssured.port=port;
        // Setup steps if necessary
    }

    @When("the client calls \\/public")
    public void theClientCallsPublic() {
        response = RestAssured.get(PUBLIC_URL);
    }

    @Then("the response should be {string}")
    public void theResponseShouldBe(String expectedResponse) {
        String actualResponse = response.getBody().asString();
        assertThat(actualResponse, equalTo(expectedResponse));
    }

    @Given("secrets not passed")
    public void secretsNotPassed() {
        // do nothing
    }

    @When("the client calls \\/secured")
    public void theClientCallsSecuredEndpoint() {
        response = RestAssured.get(SECURED_URL);
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int arg0) {
        int actualStatusCode = response.getStatusCode();
        assertThat(actualStatusCode, equalTo(arg0));
    }

    @When("the client calls \\/secured for authenticated request")
    public void theClientCallsSecuredForAuthenticatedRequest() {
        String accessToken = keycloakSimpleApi.tokenManager().grantToken().getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        response = RestAssured.given().headers(headers).get(SECURED_URL);
    }

    @When("the client calls \\/secured for unauthenticated request with invalid token")
    public void theClientCallsSecuredForUnauthenticatedRequestWithInvalidToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer abcdef");
        response = RestAssured.given().headers(headers).get(SECURED_URL);
    }
}
