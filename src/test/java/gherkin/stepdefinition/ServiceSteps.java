package gherkin.stepdefinition;

import br.com.core.report.ExtentReports;
import br.com.core.setup.DriverManager;
import br.com.pom.jsonplaceholder.Book;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.testng.AssertJUnit.assertEquals;

public class ServiceSteps extends DriverManager {

    private Book book;
    private Book expectedBook;
    private Response response;

    @When("^I send a Get request to API \"([^\"]*)\" with code (\\d+)$")
    public void iSendAGetRequestToAPIWithCode(String url, int param) throws Throwable {
        RequestSpecification httpRequest = given();
        String completeUrl = url + param;
        ExtentReports.appendToReport("Request: " + completeUrl);
        response = httpRequest.get(completeUrl);
        book = response.as(Book.class, ObjectMapperType.GSON);
        expectedBook = new Book(1L, false, "delectus aut autem", 1L);
    }

    @When("^I send a Post request to API \"([^\"]*)\"$")
    public void iSendAPostRequestToAPI(String url, String body) throws Throwable {
        RequestSpecification httpRequest = given();
        httpRequest.config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("charset=UTF-8", ContentType.JSON)));
        httpRequest.body(body);
        response = httpRequest.post(url);
        ExtentReports.appendToReport("Request: " + url +"\n " + body);
    }
    
    @When("^I send a Put request to API \"([^\"]*)\"$")
    public void iSendAPutRequestToAPI(String url, String body) throws Throwable {
        RequestSpecification httpRequest = given();
        httpRequest.config(RestAssured.config().encoderConfig(encoderConfig().encodeContentTypeAs("charset=UTF-8", ContentType.JSON)));
        httpRequest.body(body);
        response = httpRequest.put(url);
        ExtentReports.appendToReport("Request: " + url +"\n " + body);
    }

    @When("^I send a Delete request to API \"([^\"]*)\"$")
    public void iSendADeleteRequestToAPI(String url) throws Throwable {
        RequestSpecification httpRequest = given();
        ExtentReports.appendToReport("Request: " + url);
        response = httpRequest.delete(url);
    }

    @Then("^the HTTP Status (\\d+) will be returned with some description data of a book$")
    public void theHTTPStatusWillBeReturnedWithSomeDescriptionDataOfABook(int responseHttp) {
        ExtentReports.appendToReport("Status Http: " + response.getStatusCode());
        ExtentReports.appendToReport(response.asString());
        assertThat(book, samePropertyValuesAs(expectedBook));
        assertEquals(responseHttp, response.getStatusCode());
    }

    @Then("^the HTTP Status (\\d+) will be returned$")
    public void theHTTPStatusWillBeReturned(int responseHttp) throws Throwable {
        ExtentReports.appendToReport("Status Http: " + response.getStatusCode());
        ExtentReports.appendToReport(response.asString());
        assertEquals(responseHttp, response.getStatusCode());
    }
}
