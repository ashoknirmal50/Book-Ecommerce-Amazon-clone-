import java.text.DecimalFormat;
import java.sql.*;

public class TotalPrice {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cart";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final double TAX_RATE = 0.12;
    private static final double SHIPPING_FEES = 50.0;

    public static void main(String[] args) throws SQLException {
        DecimalFormat df = new DecimalFormat("#.##");
        Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM details");
        double totalPrice = 0.0;
        while (resultSet.next()) {
            int quantity = resultSet.getInt("quantity");
            double price = resultSet.getDouble("price");
            double subtotal = quantity * price;
            totalPrice += subtotal;
        }

        double tax = totalPrice * TAX_RATE;
        double totalCost = totalPrice + tax + SHIPPING_FEES;
        System.out.println("Total Price: Rs. " + df.format(totalPrice));
        System.out.println("Tax (12%): Rs. " + df.format(tax));
        System.out.println("Shipping Fees: Rs. " + df.format(SHIPPING_FEES));
        System.out.println("Total Cost (including tax and shipping fees): Rs. " + df.format(totalCost));
        statement.executeUpdate("DELETE FROM details");
    }
 }
