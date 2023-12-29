package shoppingprocess.models;

import product.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id; // optional for now
    private int productId;
    private int userId;
    private int quantity;
    private double totalPrice;

    public Order(int productId, int userId, int quantity, double totalPrice) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public Order(int id, int productId, int userId, int quantity, double totalPrice) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void displayInfo() throws Exception {
        Product product = Product.getProductById(productId);
        System.out.println("Product: " + product.getName() + " | " + product.getCategory());
        System.out.println("Quantity: " + quantity);
        System.out.println("Total Price: " + totalPrice + "$");
    }

    /**
     * Saves the order details to the database.
     */
    public void save() {
        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);

            String insertOrderQuery = "INSERT INTO purchase (user_id, product_id, quantity, total_amount) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertOrderQuery);
            statement.setInt(1, getUserId());
            statement.setInt(2, getProductId());
            statement.setInt(3, getQuantity());
            statement.setDouble(4, getTotalPrice());
            statement.executeUpdate();
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    public static List<Order> list() {
        return new ArrayList<>();
    }

    public static Order get(int id) {
        return null;
    }

    public static Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new Order(resultSet.getInt("id"), resultSet.getInt("product_id"), resultSet.getInt("user_id"),
                resultSet.getInt("quantity"), resultSet.getDouble("total_amount"));
    }

}
