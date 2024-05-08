import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.apache.poi.ss.usermodel.*;

public class ExcelToSQL {
    public static void main(String[] args) {
        String excelFilePath = "C:\\Users\\hp\\Downloads\\admin module\\admin";
        String jdbcURL = "jdbc:mysql://localhost:3306/db";
        String username = "root";
        String password = "richagupta";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            File file = new File(excelFilePath);
            FileInputStream inputStream = new FileInputStream(file);

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS my_table (title VARCHAR(255), author VARCHAR(255), quantity INT)";
            connection.createStatement().executeUpdate(createTableSQL);

            String insertSQL = "INSERT INTO my_table (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            for (Row row : sheet) {
                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);
                Cell cell3 = row.getCell(2);

                if (cell1 != null && cell2 != null && cell3 != null) {
                    String value1 = "";
                    String value2 = "";
                    String value3 = "";

                    switch (cell1.getCellType()) {
                        case STRING:
                            value1 = cell1.getStringCellValue();
                            break;
                        case NUMERIC:
                            value1 = String.valueOf(cell1.getNumericCellValue());
                            break;
                        // Handle other cell types if necessary
                    }

                    switch (cell2.getCellType()) {
                        case STRING:
                            value2 = cell2.getStringCellValue();
                            break;
                        case NUMERIC:
                            value2 = String.valueOf(cell2.getNumericCellValue());
                            break;
                        // Handle other cell types if necessary
                    }

                    switch (cell3.getCellType()) {
                        case STRING:
                            value3 = cell3.getStringCellValue();
                            break;
                        case NUMERIC:
                            value3 = String.valueOf(cell3.getNumericCellValue());
                            break;
                        // Handle other cell types if necessary
                    }

                    preparedStatement.setString(1, value1);
                    preparedStatement.setString(2, value2);
                    preparedStatement.setString(3, value3);
                    preparedStatement.addBatch();
                }
            }

            preparedStatement.executeBatch();

            workbook.close();
            inputStream.close();

            System.out.println("Data imported successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
