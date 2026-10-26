
package com.banking.api;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

    public static void main(String[] args) throws IOException {

        String filePath =
                "src/test/java/com/banking/resoureces/ProductTestData.xlsx";

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheet("Product");

            if (sheet == null) {
                System.out.println("Error: Product sheet not found.");
                return;
            }

            DataFormatter formatter = new DataFormatter();

            // Start at row 1 to skip the header
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

                System.out.println("Test Case: " + testCase);
                System.out.println("Product ID: " + productId);
                System.out.println("Expected Status: " + expectedStatus);
                System.out.println("Expected Title: " + expectedTitle);
                System.out.println("-------------------------");
            }
        }
    }
}
