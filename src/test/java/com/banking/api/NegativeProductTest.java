
package com.banking.api;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import io.restassured.response.Response;

public class NegativeProductTest {

    @Test
    public void verifyNonExistingProduct() {

        ProductService productService = new ProductService();

        Response response = productService.getProductById(99999);

        // Check HTTP status
        assertEquals(200, response.getStatusCode());

        // Check response body is empty
        assertEquals("", response.asString().trim());
    }


@Test
public void verifyNegativeProductId() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(-1);

    // Validate HTTP status
    assertEquals(200, response.getStatusCode());

    // Validate response body is empty
    assertEquals("", response.asString().trim());
}




@Test
public void verifyNonNumericProductId() {

    ProductService productService = new ProductService();

    Response response =
            productService.getProductByIdAsString("abc");

    ApiValidator.validateNegativeResponse(response, 200);
}



@Test
public void verifyProductResponseTime() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    System.out.println(
            "API Response Time: " + response.getTime() + " ms");

    ApiValidator.validateResponseTime(response, 2000);
}


@Test
public void verifyProductContentType() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    System.out.println(
            "Content-Type: " + response.getContentType());

    ApiValidator.validateContentType(response, "application/json");
}


@Test
public void verifyContentTypeHeader() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    String contentType = response.getHeader("Content-Type");

    System.out.println("Content-Type header: " + contentType);

    ApiValidator.validateHeader(
            response,
            "Content-Type",
            contentType);
}


@Test
public void verifyProductIdInJsonResponse() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    int actualId = response.jsonPath().getInt("id");

    System.out.println("Actual Product ID: " + actualId);

    org.junit.Assert.assertEquals(1, actualId);
}


@Test
public void verifyProductTitleInJsonResponse() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    String actualTitle = response.jsonPath().getString("title");

    String expectedTitle =
            "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops";

    System.out.println("Actual Product Title: " + actualTitle);

    org.junit.Assert.assertEquals(expectedTitle, actualTitle.trim());
}


@Test
public void verifyProductPriceIsGreaterThanZero() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    double actualPrice = response.jsonPath().getDouble("price");

    System.out.println("Actual Product Price: " + actualPrice);

    org.junit.Assert.assertTrue(
            "Product price should be greater than zero",
            actualPrice > 0);
}


@Test
public void verifyProductCategory() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    String actualCategory =
            response.jsonPath().getString("category");

    String expectedCategory = "men's clothing";

    System.out.println("Actual Product Category: "
            + actualCategory);

    org.junit.Assert.assertEquals(
            expectedCategory,
            actualCategory);
}


@Test
public void verifyCompleteProductResponse() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    // Validate product ID
    org.junit.Assert.assertEquals(
            1,
            response.jsonPath().getInt("id"));

    // Validate product title
    org.junit.Assert.assertEquals(
            "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            response.jsonPath().getString("title").trim());

    // Validate product price
    double price = response.jsonPath().getDouble("price");

    org.junit.Assert.assertTrue(
            "Price should be greater than zero",
            price > 0);

    // Validate product category
    org.junit.Assert.assertEquals(
            "men's clothing",
            response.jsonPath().getString("category"));

    System.out.println("Complete product response validation passed");
}


@Test
public void verifyProductFieldExists() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    boolean idExists =
            response.jsonPath().get("id") != null;

    boolean titleExists =
            response.jsonPath().get("title") != null;

    boolean priceExists =
            response.jsonPath().get("price") != null;

    boolean categoryExists =
            response.jsonPath().get("category") != null;

    org.junit.Assert.assertTrue("id field is missing", idExists);
    org.junit.Assert.assertTrue("title field is missing", titleExists);
    org.junit.Assert.assertTrue("price field is missing", priceExists);
    org.junit.Assert.assertTrue("category field is missing", categoryExists);

    System.out.println("All required product fields exist");
}


@Test
public void verifyProductPriceIsNotNull() {

    ProductService productService = new ProductService();

    Response response = productService.getProductById(1);

    Object actualPrice = response.jsonPath().get("price");

    org.junit.Assert.assertNotNull(
            "Product price should not be null",
            actualPrice);

    System.out.println("Product price is not null: " + actualPrice);
}


@Test
public void verifyMissingJsonField() {

    String jsonResponse = """
            {
                "id": 1,
                "title": "Backpack",
                "price": 109.95
            }
            """;

    io.restassured.path.json.JsonPath jsonPath =
            new io.restassured.path.json.JsonPath(jsonResponse);

    boolean categoryExists =
            jsonPath.getMap("$").containsKey("category");

    org.junit.Assert.assertFalse(
            "Category field should be missing",
            categoryExists);

    System.out.println("Category field is missing as expected");
}













}
