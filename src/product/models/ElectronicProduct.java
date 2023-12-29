package product.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ElectronicProduct extends Product {
    private String brand;
    private int warranty;
    private String color;

    public ElectronicProduct(String name, double price, int stock, int userId, String brand, int warranty, String color) {
        super(name, price, stock, userId);
        this.brand = brand;
        this.warranty = warranty;
        this.color = color;
    }

    public ElectronicProduct(int id, String name, double price, int stock, int userId, String brand, int warranty, String color) {
        super(id, name, price, stock, userId);
        this.brand = brand;
        this.warranty = warranty;
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return "Electronic Product";
    }

    public void update(Map<String, String> data) {
        super.update(data);
        if (data.containsKey("brand")) {
            setBrand(data.get("brand"));
        }
        if (data.containsKey("warranty")) {
            setWarranty(Integer.parseInt(data.get("warranty")));
        }
        if (data.containsKey("color")) {
            setColor(data.get("color"));
        }
    }

    public void displayInfo() {
        System.out.printf("Electronic Device: %s | ID: %d | %.2f $ | Brand: %s | Warranty: %d years | Color: %s\n", getName(), getId(), getPrice(), getBrand(), getWarranty(), getColor());
    }

    public static ElectronicProduct createInstance(Map<String, String> data) {
        return new ElectronicProduct(data.get("name"), Float.parseFloat(data.get("price")),
                Integer.parseInt(data.get("stock")), Integer.parseInt(data.get("userId")),
                data.get("brand"), Integer.parseInt(data.get("warranty")), data.get("color"));
    }

    public void save() {
        int id = getId();
        super.save();
        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);

            if (id == 0) {

                String insertElectronicProductQuery = "INSERT INTO electronicProduct (product_id, brand, warranty, color) VALUES (?, ?, ?, ?)";

                PreparedStatement statement = connection.prepareStatement(insertElectronicProductQuery);
                statement.setInt(1, getId());
                statement.setString(2, getBrand());
                statement.setInt(3, getWarranty());
                statement.setString(4, getColor());

                statement.executeUpdate();
            } else {
                String updateElectronicProductQuery = "UPDATE electronicProduct SET brand = ?, warranty = ?, color = ? WHERE product_id = ?";
                PreparedStatement statement = connection.prepareStatement(updateElectronicProductQuery);
                statement.setString(1, getBrand());
                statement.setInt(2, getWarranty());
                statement.setString(3, getColor());
                statement.setInt(4, getId());

                statement.executeUpdate();
            }

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

    public static List<Product> list() {
        List<Product> electronicProducts = new ArrayList<>();
        Connection connection = db.DatabaseUtils.getDbConnection();

        try {
            String retrieveQuery = "SELECT product.*, electronicProduct.* " +
                    "FROM product " +
                    "JOIN electronicProduct ON product.id = electronicProduct.product_id";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ElectronicProduct electronicProduct = createElectronicProductFromResultSet(resultSet);
                electronicProducts.add(electronicProduct);
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
        electronicProducts.sort(Comparator.comparingInt(Product::getId));
        return electronicProducts;
    }

    public static ElectronicProduct createElectronicProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new ElectronicProduct(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getFloat("price"),
                resultSet.getInt("stock"), resultSet.getInt("user_id"),
                resultSet.getString("brand"), resultSet.getInt("warranty"), resultSet.getString("color"));
    }

}
