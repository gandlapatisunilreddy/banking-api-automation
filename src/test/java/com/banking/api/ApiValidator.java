
package com.banking.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.restassured.response.Response;

public class ApiValidator {

    public void verifyStatusCode(
            int actualStatus, int expectedStatus) {
        assertEquals(expectedStatus, actualStatus);
    }

    public void verifyFieldIsNotNull(Object actualValue) {
        assertNotNull(actualValue);
    }

    public void verifyResponseFieldIsNotNull(
            Response response, String jsonPath) {

        Object actualValue = response.path(jsonPath);

        assertNotNull(
            "Expected field '" + jsonPath + "' should not be null",
            actualValue
        );
    }

    public void verifyFieldEquals(
            Object actualValue, Object expectedValue) {
        assertEquals(expectedValue, actualValue);
    }

    public void verifyResponseBodyIsEmpty(String responseBody) {
        assertEquals("", responseBody);
    }


public static void validateNegativeResponse(
        io.restassured.response.Response response,
        int expectedStatusCode) {

    org.junit.Assert.assertEquals(
            "Unexpected HTTP status",
            expectedStatusCode,
            response.getStatusCode());

    org.junit.Assert.assertTrue(
            "Expected an empty response body",
            response.asString().trim().isEmpty());
}


public static void validateResponseTime(
        io.restassured.response.Response response,
        long maxTimeInMillis) {

    org.junit.Assert.assertTrue(
            "API response time exceeded "
                    + maxTimeInMillis + " ms",
            response.getTime() < maxTimeInMillis);
}


public static void validateContentType(
        io.restassured.response.Response response,
        String expectedContentType) {

    org.junit.Assert.assertTrue(
            "Unexpected Content-Type: "
                    + response.getContentType(),
            response.getContentType()
                    .contains(expectedContentType));
}


public static void validateHeader(
        io.restassured.response.Response response,
        String headerName,
        String expectedValue) {

    String actualValue = response.getHeader(headerName);

    org.junit.Assert.assertEquals(
            "Unexpected value for header: " + headerName,
            expectedValue,
            actualValue);
}















}
