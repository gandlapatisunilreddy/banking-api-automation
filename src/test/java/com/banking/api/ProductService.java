
package com.banking.api;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class ProductService extends BaseTest {

    // GET: Retrieve a product by ID
    public Response getProductById(int productId) {

        return given()
                .spec(getRequestSpec())
            .when()
                .get("/products/" + productId);
    }


    // POST: Create a product
    public Response createProduct(String requestBody) {

        return given()
                .spec(getRequestSpec())
                .contentType("application/json")
                .body(requestBody)
            .when()
                .post("/products");
    }


    // PUT: Update a product
    public Response updateProduct(
            int productId, String requestBody) {

        return given()
                .spec(getRequestSpec())
                .contentType("application/json")
                .body(requestBody)
            .when()
                .put("/products/" + productId);
    }


    // PATCH: Partially update a product
    public Response patchProduct(
            int productId, String requestBody) {

        return given()
                .spec(getRequestSpec())
                .contentType("application/json")
                .body(requestBody)
            .when()
                .patch("/products/" + productId);
    }


    // DELETE: Delete a product
    public Response deleteProduct(int productId) {

        return given()
                .spec(getRequestSpec())
            .when()
                .delete("/products/" + productId);
    }
    
    
public Response getProductByIdAsString(String productId) {
    return given()
            .spec(getRequestSpec())
            .get("/products/" + productId);
}


}
