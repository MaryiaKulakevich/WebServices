package by.epam.webservices.tests;

import by.epam.webservices.bo.Address;
import by.epam.webservices.bo.User;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestAssuredTest {

    private Response response;

    @BeforeClass
    public void beforeTest(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        response = given().get("/users").andReturn();
    }

    @Test
    public void checkStatusCode(){
        int statusCode = response.getStatusCode();
        String statusLine = response.getStatusLine();
        System.out.println(statusLine);
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void checkResponseHeader(){
        Headers allHeaders = response.getHeaders();
        boolean doesContentTypeHeaderExist = allHeaders.hasHeaderWithName("content-type");
        Assert.assertTrue(doesContentTypeHeaderExist);
        String valueOfContentTypeHeader = response.getHeader("content-type");
        Assert.assertTrue(valueOfContentTypeHeader.contains("application/json; charset=utf-8"));
    }

    @Test
    public void checkResponseBody(){
        User[] users = response.as(User[].class);
        Address address = users[0].getAddress();
        System.out.println(address.getCity());
        Assert.assertEquals(users.length,10);
    }
}
