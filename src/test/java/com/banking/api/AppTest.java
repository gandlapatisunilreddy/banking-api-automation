
package com.banking.api;

import org.junit.Test;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

public class AppTest extends BaseTest {

    private final ProductService productService =
            new ProductService();

    private final ResponseSpec responseSpec =
            new ResponseSpec();

    private final ApiValidator apiValidator =
            new ApiValidator();


    @Test
    public void getProductTest() {

        Response response =
                productService.getProductById(1);

        response.then()
                .spec(responseSpec.getResponseSpec(200));

        apiValidator.verifyResponseFieldIsNotNull(
                response, "title");

        int productId = response.path("id");

        apiValidator.verifyFieldEquals(productId, 1);

        System.out.println("Product ID: " + productId);
        System.out.println(
                "Product Title: " + response.path("title"));
    }


    @Test
    public void createProductTest() {

        String requestBody =
                "{\"title\":\"Test Product\",\"price\":100}";

        Response response =
                productService.createProduct(requestBody);

        response.then()
                .spec(responseSpec.getResponseSpec(201));

        apiValidator.verifyFieldEquals(
                response.path("title"), "Test Product");

        apiValidator.verifyFieldEquals(
                ((Number) response.path("price")).intValue(),
                100);
    }


    @Test
    public void updateProductTest() {

        String requestBody =
                "{\"title\":\"Updated Product\",\"price\":200}";

        Response response =
                productService.updateProduct(1, requestBody);

        response.then()
                .spec(responseSpec.getResponseSpec(200))
                .body("title", equalTo("Updated Product"))
                .body("price", equalTo(200));
    }


    @Test
    public void patchProductTest() {

        String requestBody =
                "{\"title\":\"Patched Product\"}";

        Response response =
                productService.patchProduct(1, requestBody);

        response.then()
                .spec(responseSpec.getResponseSpec(200))
                .body("title", equalTo("Patched Product"));
    }


    @Test
    public void deleteProductTest() {

        Response response =
                productService.deleteProduct(1);

        response.then()
                .spec(responseSpec.getResponseSpec(200));
    }


    @Test
    public void createProductWithoutTitleTest() {

        String requestBody = "{\"price\":100}";

        Response response =
                productService.createProduct(requestBody);

        response.then()
                .spec(responseSpec.getResponseSpec(201))
                .body("price", equalTo(100))
                .body("title", equalTo(null));
    }


    @Test
    public void getInvalidProductTest() {

        Response response =
                productService.getProductById(999999);

        apiValidator.verifyStatusCode(
                response.getStatusCode(), 200);

        apiValidator.verifyResponseBodyIsEmpty(
                response.asString());
    }
}
