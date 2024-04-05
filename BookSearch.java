package AmazonClone;

import java.sql.*;
import java.util.Scanner;

public class BookSearch {
    private DatabaseConnector connector;
    private Scanner scanner;

    public BookSearch() {
        connector = new DatabaseConnector();
        scanner = new Scanner(System.in);
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
                ResultSet resultSet = connector.searchBookByName(name);
                displayBooks(resultSet);
                
            } else {
                System.out.println("Enter author name: ");
                String author = scanner.nextLine();
                ResultSet resultSet = connector.searchBookByAuthor(author);
                displayBooks(resultSet);
                
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
    

    private void displayBooks(ResultSet resultSet) throws SQLException {
        boolean foundBooks = false;
        while (resultSet.next()) {
            foundBooks = true;
            String title = resultSet.getString("book_name");
            String author = resultSet.getString("author_name");
            double price = resultSet.getDouble("price");
            int quantity = resultSet.getInt("quantity");

            System.out.println("Title: " + title);
            System.out.println("Author: " + author);
            System.out.println("Price: " + price);
            if (quantity > 0) {
                System.out.println("Available Quantity: " + quantity);

                System.out.println("Do you want to search again? (yes/no)");
                String newScearch = scanner.nextLine();
                if(newScearch.equalsIgnoreCase("yes")){
                    searchBooks();
                }else{
                    System.out.println("Thank You");
                }


                
            } else {
                System.out.println("Not in stock");
                System.out.println("Search another book ? (yes/no)");
                String newScearch = scanner.nextLine();
                if(newScearch.equalsIgnoreCase("yes")){
                    searchBooks();
                }
            }
            System.out.println();
        }
        if (!foundBooks) {
            System.out.println("No books found.");
            System.out.println("Search another book ? (yes/no)");
            String newScearch = scanner.nextLine();
                if(newScearch.equalsIgnoreCase("yes")){
                    searchBooks();
                }

        }
    }

    

    public static void main(String[] args) {
        BookSearch bookSearch = new BookSearch();
        bookSearch.searchBooks();
    }
}
