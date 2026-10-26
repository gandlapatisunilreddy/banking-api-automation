
package com.banking.api;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

import io.restassured.response.Response;

public class ExcelDrivenProductTest {

    private final ProductService productService =
            new ProductService();

    @Test
    public void verifyProductsUsingExcel() throws IOException {

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

                System.out.println(
                        "\nExecuting test case: " + testCase);

                Response response =
                        productService.getProductById(
                                Integer.parseInt(productId));

                assertEquals(
                        "Status mismatch for " + testCase,
                        Integer.parseInt(expectedStatus),
                        response.getStatusCode());

                if ("EMPTY".equalsIgnoreCase(expectedTitle)) {

                    assertEquals(
                            "Expected empty body for " + testCase,
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

                System.out.println(
                        "PASS: " + testCase
                        + " | Product ID: " + productId);
            }
        }
    }
}
