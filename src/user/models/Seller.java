package user.models;

import product.models.BookProduct;
import product.models.ElectronicProduct;
import product.models.MusicalProduct;
import product.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Seller extends User {
    public Seller(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, "seller");
    }

    public static User createInstance(Map<String, String> data){
        return new Seller(data.get("firstName"), data.get("lastName"),
                data.get("email"), data.get("password"));
    }

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
}
