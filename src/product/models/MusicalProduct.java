package product.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MusicalProduct extends Product {
    private String instrumentType;
    private String brand;
    private String color;

    public MusicalProduct(String name, double price, int stock, int userId, String instrumentType, String brand, String color) {
        super(name, price, stock, userId);
        this.instrumentType = instrumentType;
        this.brand = brand;
        this.color = color;
    }

    public MusicalProduct(int id, String name, double price, int stock, int userId, String instrumentType, String brand, String color) {
        super(id, name, price, stock, userId);
        this.instrumentType = instrumentType;
        this.brand = brand;
        this.color = color;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return "Musical Product";
    }

    public void update(Map<String, String> data) {
        super.update(data);
        if (data.containsKey("instrumentType")) {
            setInstrumentType(data.get("instrumentType"));
        }
        if (data.containsKey("brand")) {
            setBrand(data.get("brand"));
        }
        if (data.containsKey("color")) {
            setColor(data.get("color"));
        }
    }

    public void displayInfo() {
        System.out.printf("Musical Instrument: %s | ID: %d | %.2f $ | Brand: %s | Instrument Type: %s | Color: %s\n", getName(), getId(), getPrice(), getBrand(), getInstrumentType(), getColor());
    }

    public static MusicalProduct createInstance(Map<String, String> data) {
        return new MusicalProduct(data.get("name"), Float.parseFloat(data.get("price")), Integer.parseInt(data.get("stock")),
                Integer.parseInt(data.get("userId")), data.get("instrumentType"), data.get("brand"), data.get("color"));
    }

    public void save() {
        int id = getId();
        super.save();

        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);

            if (id == 0) {
                String insertMusicalQuery = "INSERT INTO musicalProduct (product_id, instrument_type, brand, color) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insertMusicalQuery);
                statement.setInt(1, getId());
                statement.setString(2, getInstrumentType());
                statement.setString(3, getBrand());
                statement.setString(4, getColor());

                statement.executeUpdate();
            } else {
                String updateMusicalProductQuery = "UPDATE musicalProduct SET instrument_type = ?, brand = ?, color = ? WHERE product_id = ?";
                PreparedStatement statement = connection.prepareStatement(updateMusicalProductQuery);
                statement.setString(1, getInstrumentType());
                statement.setString(2, getBrand());
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
        List<Product> musicalProducts = new ArrayList<>();
        Connection connection = db.DatabaseUtils.getDbConnection();

        try {
            String retrieveQuery = "SELECT product.*, musicalProduct.* " +
                    "FROM product " +
                    "JOIN musicalProduct ON product.id = musicalProduct.product_id";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                MusicalProduct musicalProduct = createMusicalProductFromResultSet(resultSet);
                musicalProducts.add(musicalProduct);
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
        musicalProducts.sort(Comparator.comparingInt(Product::getId));
        return musicalProducts;
    }

    public static MusicalProduct createMusicalProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new MusicalProduct(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getFloat("price"),
                resultSet.getInt("stock"), resultSet.getInt("user_id"),
                resultSet.getString("instrument_type"), resultSet.getString("brand"), resultSet.getString("color"));
    }
}
