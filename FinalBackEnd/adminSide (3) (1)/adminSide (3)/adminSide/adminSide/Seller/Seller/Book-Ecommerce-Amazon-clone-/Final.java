import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.*;

public class Final {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/BookStore";
    String excelFilePath = "C:\\Users\\Home\\Downloads\\bookEcommerce\\BookEcommerce\\Books.xlsx";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final double TAX_RATE = 0.12;
    private static final double SHIPPING_FEES = 50.0;

    private Connection connection;
    private Statement statement;
    private Scanner scanner;

    public Final() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            scanner = new Scanner(System.in);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void importDataFromExcel() {
        try {
            File file = new File(excelFilePath);
            FileInputStream inputStream = new FileInputStream(file);

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            String createTableSQL = "CREATE TABLE IF NOT EXISTS books (title VARCHAR(255), author VARCHAR(255),price int, quantity INT)";
            connection.createStatement().executeUpdate(createTableSQL);

            String insertSQL = "INSERT INTO books (title, author,price, quantity) VALUES (?, ?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            for (Row row : sheet) {
                Cell cell1 = row.getCell(0);
                Cell cell2 = row.getCell(1);
                Cell cell3 = row.getCell(2);
                Cell cell4 = row.getCell(3);

                if (cell1 != null && cell2 != null && cell3 != null && cell4!=null) {
                    String value1 = "";
                    String value2 = "";
                    String value3 = "";
                    String value4 = "";

                    value1 = cell1.getStringCellValue();
                    value2 = cell2.getStringCellValue();
                    value3 = String.valueOf(cell3.getNumericCellValue());
                    value4 = String.valueOf(cell4.getNumericCellValue());

                    preparedStatement.setString(1, value1);
                    preparedStatement.setString(2, value2);
                    preparedStatement.setString(3, value3);
                    preparedStatement.setString(4, value4);
                    preparedStatement.addBatch();
                }
            }

            preparedStatement.executeBatch();

            workbook.close();
            inputStream.close();

            System.out.println("Data imported successfully.");

        } catch (Exception exception) {}
    }

    public void  userInterface(){
        System.out.println("Enter 1 to use admin side, Enter 2 to use user side,Enter 3 to Log out");
        String preference=scanner.nextLine();
        if(preference.equals("1")){
            importDataFromExcel();
            userInterface();
        }
        else if(preference.equals("2")){
            searchBooks();
        }
        else if(preference.equals("3")){
            System.out.println("Thank you for shopping");
        }
        else{
            System.out.println("Enter valid preference");
        }
    }

    public void searchBooks() {
        try {
            System.out.println("Enter 1 to search by name, 2 to search by author: ");
            String choice = scanner.nextLine();

            if (!choice.equals("1") && !choice.equals("2")) {
                System.out.println("Invalid input! Please enter either 1 or 2.");
                searchBooks();
                return;
            }

            if (choice.equals("1")) {
                System.out.println("Enter book name: ");
                String name = scanner.nextLine();
                System.out.println();
                searchBookByName(name);
            } else {
                System.out.println("Enter author name: ");
                String author = scanner.nextLine();
                System.out.println();
                searchBookByAuthor(author);
            }
        } catch (Exception exception) {}
    }

    public void searchBookByName(String name) {
        try {
            String query = "SELECT * FROM books WHERE title LIKE '%" + name + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            displayBooks(resultSet);
        } catch (SQLException exception) {}
    }

    public void searchBookByAuthor(String author) {
        try {
            String query = "SELECT * FROM books WHERE author LIKE '%" + author + "%'";
            ResultSet resultSet = statement.executeQuery(query);
            displayBooks(resultSet);
        } catch (SQLException exception) {}
    }

    public void displayBooks(ResultSet resultSet) {
        try {
            boolean isFoundBooks=false;
            while (resultSet.next()) {
                isFoundBooks=true;
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");

                System.out.println("Title: " + title);
                System.out.println("Author: " + author);
                System.out.println("Price: " + price);
                System.out.println("Quantity: " + quantity);
                System.out.println();
                if (quantity > 0) {
                    purchaseBook(title, price, quantity); 
                } else {
                    System.out.println("The requested book is not in stock");
                    System.out.println();
                    askContinue(); 
                }

            }
            if(!isFoundBooks){
                System.out.println("No books found");
                System.out.println("Do you want to continue search?(yes/no)");
                String newSearch=scanner.nextLine();
                if(newSearch.equalsIgnoreCase("yes")){
                        searchBooks();;
                }
            }else{
                System.out.println("Thanks for Shopping");
            }
        } catch (SQLException exception) {}

    }
    public void purchaseBook(String title, double price, int availableQuantity) throws SQLException{
        try {
            System.out.println("Enter the quantity you want to purchase: ");
            int purchaseQuantity = Integer.parseInt(scanner.nextLine());

            if (purchaseQuantity <= availableQuantity) {
                addToCart(title, price, purchaseQuantity);
                int remainingQuantity = availableQuantity - purchaseQuantity;
                PreparedStatement updateStatement = connection.prepareStatement("UPDATE books SET quantity = ? WHERE title = ?");
                updateStatement.setInt(1, remainingQuantity);
                updateStatement.setString(2, title);
                updateStatement.executeUpdate();
                updateStatement.close();
                askContinue(); 
            } else {
                System.out.println("Requested quantity exceeds available stock.");
                System.out.println("Available Stock: " + availableQuantity);
                purchaseBook(title, price, availableQuantity); 
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid quantity.");
            purchaseBook(title, price, availableQuantity); 
        }
    }
    public void addToCart(String title, double price, int quantity) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO details (title, quantity, price) VALUES (?, ?, ?)");
            insertStatement.setString(1, title);
            insertStatement.setInt(2, quantity);
            insertStatement.setDouble(3, price);
            insertStatement.executeUpdate();
            insertStatement.close();

            System.out.println("Product added to cart:");
            System.out.println("Title: " + title);
            System.out.println("Price per unit: " + price);
            System.out.println("Quantity: " + quantity);
            System.out.println();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void askContinue() {
        System.out.println("Do you want to continue purchasing? (yes/no)");
        String continueChoice = scanner.nextLine().toLowerCase();
        if (continueChoice.equals("yes")) {
            searchBooks(); 
        } else {
            calculateTotalCost(); 
        }
    }

    public void calculateTotalCost() {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM details");
            double totalPrice = 0.0;
            while (resultSet.next()) {
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                totalPrice += quantity * price;
            }

            double tax = totalPrice * TAX_RATE;
            double totalCost = totalPrice + tax + SHIPPING_FEES;
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("                            Total Price:             Rs." + totalPrice);
            System.out.println("                            Tax (12%):               Rs. " + tax);
            System.out.println("                            Shipping Fees:           Rs. " + SHIPPING_FEES);
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Total Cost (including tax and shipping fees):        Rs. " + totalCost);
            System.out.println("--------------------------------------------------------------------------");
            statement.executeUpdate("DELETE FROM details");
        } 
        catch (Exception exception) {}
    }

    public static void main(String[] args) {
        Final ecommerce = new Final();
        ecommerce.userInterface();
    }
}