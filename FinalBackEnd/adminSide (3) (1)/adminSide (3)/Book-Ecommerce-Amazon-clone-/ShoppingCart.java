import java.sql.*;

public class ShoppingCart {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cart";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args)throws Exception {
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        System.out.println("Connected to the database.");
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS details (" +
               "product_name VARCHAR(100)," +
                "quantity INT," +
                "price DECIMAL(10,2))");
        PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO details (productName, quantity, price) VALUES (?, ?, ?)");
        while (true) {
            System.out.println("Enter the product name (type 'exit' to finish adding products):");
            String productName = System.console().readLine();
            if (productName.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println("Enter the quantity:");
            int quantity = Integer.parseInt(System.console().readLine());

            System.out.println("Enter the price:");
            double price = Double.parseDouble(System.console().readLine());

            insertStatement.setString(1, productName);
            insertStatement.setInt(2, quantity);
            insertStatement.setDouble(3, price);
            insertStatement.executeUpdate();

            System.out.println("Product added successfully.");
            }
    }
}
                        