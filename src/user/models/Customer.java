package user.models;

import shoppingprocess.models.Order;
import shoppingprocess.models.ProductItem;
import shoppingprocess.models.ShoppingCart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Customer extends User {
    private ShoppingCart shoppingCart;

    public Customer(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "customer");
        this.shoppingCart = new ShoppingCart(getId());
    }

    public static User createInstance(Map<String, String> data) {
        return new Customer(data.get("firstName"), data.get("lastName"),
                data.get("email"), data.get("password"));
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void addToShoppingCart(ProductItem item) {
        shoppingCart.addItem(item);
    }

    /**
     * Retrieves the list of orders placed by the customer.
     */
    public List<Order> getOrders() {
        Connection connection = db.DatabaseUtils.getDbConnection();
        List<Order> orders = new ArrayList<>();

        try {
            String retrieveQuery = "SELECT * FROM purchase WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = Order.createOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        orders.sort(Comparator.comparingInt(Order::getId));
        return orders;
    }
}
