
package com.banking.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import io.restassured.response.Response;

@RunWith(Parameterized.class)
public class ParameterizedProductTest {

    private static final Logger logger =
            LogManager.getLogger(ParameterizedProductTest.class);

    private final String testCase;
    private final int productId;
    private final int expectedStatus;
    private final String expectedTitle;

    private final ProductService productService =
            new ProductService();

    // Constructor: receives one Excel row's data
    public ParameterizedProductTest(
            String testCase,
            int productId,
            int expectedStatus,
            String expectedTitle) {

        this.testCase = testCase;
        this.productId = productId;
        this.expectedStatus = expectedStatus;
        this.expectedTitle = expectedTitle;
    }

    // Read all test data from Excel
    @Parameterized.Parameters(name = "{0} - Product ID {1}")
    public static Collection<Object[]> getTestData()
            throws IOException {

        Collection<Object[]> testData = new ArrayList<>();

        String filePath =
                "src\\test\\resources\\testdata\\ProductTestData.xlsx";

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("Product");

            if (sheet == null) {
                throw new IllegalStateException(
                        "Product sheet not found");
            }

            DataFormatter formatter = new DataFormatter();

            // Skip the header row
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String testCase =
                        formatter.formatCellValue(row.getCell(0));

                String productId =
                        formatter.formatCellValue(row.getCell(1));

                String expectedStatus =
                        formatter.formatCellValue(row.getCell(2));

                String expectedTitle =
                        formatter.formatCellValue(row.getCell(3));

                if (testCase.isBlank()
                        || productId.isBlank()
                        || expectedStatus.isBlank()
                        || expectedTitle.isBlank()) {

                    throw new IllegalArgumentException(
                            "Missing test data in Excel row " + (i + 1));
                }

                testData.add(new Object[] {
                        testCase,
                        Integer.parseInt(productId),
                        Integer.parseInt(expectedStatus),
                        expectedTitle
                });
            }
        }

        logger.info("Loaded {} test cases from Excel", testData.size());

        return testData;
    }

    @Test
    public void verifyProductUsingExcel() {

        logger.info(
                "Executing: {} | Product ID: {}",
                testCase,
                productId);

        // Send the GET request
        Response response =
                productService.getProductById(productId);

        // Log the actual HTTP status
        logger.info(
                "Received HTTP status: {} for {}",
                response.getStatusCode(),
                testCase);

        // Validate status code
        assertEquals(
                "Status mismatch for " + testCase,
                expectedStatus,
                response.getStatusCode());

        // Validate title or empty body
        if ("EMPTY".equalsIgnoreCase(expectedTitle)) {

            assertEquals(
                    "Expected empty response for " + testCase,
                    "",
                    response.asString());

        } else {

            String actualTitle =
                    response.jsonPath().getString("title");

            assertEquals(
                    "Title mismatch for " + testCase,
                    expectedTitle.trim(),
                    actualTitle.trim());
        }

        logger.info("PASS: {}", testCase);
    }
}
