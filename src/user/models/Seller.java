package user.models;

import product.models.BookProduct;
import product.models.ElectronicProduct;
import product.models.MusicalProduct;
import product.models.Product;
import shoppingprocess.models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class Seller extends User {
    public Seller(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "seller");
    }

    public static User createInstance(Map<String, String> data){
        return new Seller(data.get("firstName"), data.get("lastName"),
                data.get("email"), data.get("password"));
    }

    /**
     * Retrieves the list of products associated with the seller.
     */
    public List<Product> getProducts(){
        Connection connection = db.DatabaseUtils.getDbConnection();
        List <Product> products = new ArrayList<>();
        try {
            String retrieveQuery = "SELECT product.*, electronicProduct.* " +
                    "FROM product " +
                    "JOIN electronicProduct ON product.id = electronicProduct.product_id " +
                    "WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ElectronicProduct electronicProduct = ElectronicProduct.createElectronicProductFromResultSet(resultSet);
                products.add(electronicProduct);
            }

            retrieveQuery = "SELECT product.*, bookProduct.* " +
                    "FROM product " +
                    "JOIN bookProduct ON product.id = bookProduct.product_id " +
                    "WHERE user_id = ?";
            statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookProduct bookProduct = BookProduct.createBookProductFromResultSet(resultSet);
                products.add(bookProduct);
            }

            retrieveQuery = "SELECT product.*, musicalProduct.* " +
                    "FROM product " +
                    "JOIN musicalProduct ON product.id = musicalProduct.product_id " +
                    "WHERE user_id = ?";
            statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, getId());
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MusicalProduct musicalProduct = MusicalProduct.createMusicalProductFromResultSet(resultSet);
                products.add(musicalProduct);
            }

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
        products.sort(Comparator.comparingInt(Product::getId));
        return products;
    }

    /**
     * Retrieves the list of orders related to seller products.
     */
    public List<Order> getSales() {
        List<Product> products = getProducts();
        List<Integer> productIds = new ArrayList<>();

        for (Product product : products) {
            productIds.add(product.getId());
        }

        Connection connection = db.DatabaseUtils.getDbConnection();
        List<Order> orders = new ArrayList<>();

        try {
            String retrieveQuery = "SELECT * FROM purchase WHERE product_id IN (" +
                    String.join(",", Collections.nCopies(productIds.size(), "?")) + ")";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);

            for (int i = 0; i < productIds.size(); i++) {
                statement.setInt(i + 1, productIds.get(i));
            }

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
