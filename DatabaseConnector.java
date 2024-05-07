package AmazonClone;
// DatabaseConnector.java
import java.sql.*;

public class DatabaseConnector {
    private Connection connection;
    private Statement statement;

    public DatabaseConnector() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "Selva7kumar@@");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet searchBookByName(String name) throws SQLException {
        String query = "SELECT * FROM books WHERE book_name = '" + name + "'";
        return statement.executeQuery(query);
    }

    public ResultSet searchBookByAuthor(String author) throws SQLException {
        String query = "SELECT * FROM books WHERE author_name = '" + author + "'";
        return statement.executeQuery(query);
    }

    public void closeConnection() {
        try {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
