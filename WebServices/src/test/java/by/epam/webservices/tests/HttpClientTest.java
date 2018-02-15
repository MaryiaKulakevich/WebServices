package by.epam.webservices.tests;


import by.epam.webservices.bo.User;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class HttpClientTest {
    private CloseableHttpResponse response;

    @BeforeClass
    public void beforeTest() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jsonplaceholder.typicode.com/users");
        response = httpclient.execute(httpGet);
    }

    @Test
    public void checkStatusCode() throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void checkResponseHeader() throws IOException {
        boolean doesContentTypeHeaderExist = response.containsHeader("content-type");
        Assert.assertTrue(doesContentTypeHeaderExist);
        Header[] contentTypeHeader = response.getHeaders("content-type");
        String valueOfContentTypeHeader = contentTypeHeader[0].getValue();
        Assert.assertTrue(valueOfContentTypeHeader.contains("application/json; charset=utf-8"));
    }

    @Test
    public void checkResponseBody() throws IOException {
        HttpEntity entity = response.getEntity();
        ObjectMapper objectMapper = new ObjectMapper();
        User[] users = objectMapper.readValue(entity.getContent(), User[].class);
        Assert.assertEquals(users.length, 10);
    }

}

