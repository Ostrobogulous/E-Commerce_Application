package product.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int userId;

    public Product(String name, double price, int stock, int userId) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.userId = userId;
    }

    public Product(int id, String name, double price, int stock, int userId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return "Product";
    }

    public void update(Map<String, String> data) {
        if (data.containsKey("name")) {
            setName(data.get("name"));
        }
        if (data.containsKey("price")) {
            setPrice(Double.parseDouble(data.get("price")));
        }
        if (data.containsKey("stock")) {
            setStock(Integer.parseInt(data.get("stock")));
        }
    }

    public void save() {
        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);
            if (id == 0) {
                String insertProductQuery = "INSERT INTO product (name, price, stock, user_id) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(insertProductQuery);
                statement.setString(1, getName());
                statement.setDouble(2, getPrice());
                statement.setInt(3, getStock());
                statement.setInt(4, getUserId());
                statement.executeUpdate();
                String query = "SELECT last_insert_rowid() FROM product";
                statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery();
                int lastInsertedId = resultSet.getInt(1);
                this.id = lastInsertedId;
            } else {
                String updateProductQuery = "UPDATE product SET name = ?, price = ?, stock = ?, user_id = ? WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(updateProductQuery);
                statement.setString(1, getName());
                statement.setDouble(2, getPrice());
                statement.setInt(3, getStock());
                statement.setInt(4, getUserId());
                statement.setInt(5, getId());
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

    public void delete() {
        Connection connection = db.DatabaseUtils.getDbConnection();

        try {
            connection.setAutoCommit(false);

            String deleteProductQuery = "DELETE FROM product WHERE id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteProductQuery);
            deleteStatement.setInt(1, getId());  // Assuming getId() returns the ID of the record to be deleted
            deleteStatement.executeUpdate();

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

    public void changeQuantity(int quantity) {
        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);
            String updatePurchaseQuery = "UPDATE product SET stock = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(updatePurchaseQuery);
            statement.setInt(1, quantity);
            statement.setInt(2, getId());
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

    public void displayInfo() {
    }

    public static Product getProductById(int productId) throws Exception {
        Connection connection = db.DatabaseUtils.getDbConnection();

        try {
            String retrieveQuery = "SELECT product.*, electronicProduct.* " +
                    "FROM product " +
                    "JOIN electronicProduct ON product.id = electronicProduct.product_id " +
                    "WHERE product.id = ?";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                ElectronicProduct electronicProduct = ElectronicProduct.createElectronicProductFromResultSet(resultSet);
                return electronicProduct;
            }

            retrieveQuery = "SELECT product.*, bookProduct.* " +
                    "FROM product " +
                    "JOIN bookProduct ON product.id = bookProduct.product_id " +
                    "WHERE product.id = ?";
            statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, productId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                BookProduct bookProduct = BookProduct.createBookProductFromResultSet(resultSet);
                return bookProduct;
            }

            retrieveQuery = "SELECT product.*, musicalProduct.* " +
                    "FROM product " +
                    "JOIN musicalProduct ON product.id = musicalProduct.product_id " +
                    "WHERE product.id = ?";
            statement = connection.prepareStatement(retrieveQuery);
            statement.setInt(1, productId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                MusicalProduct musicalProduct = MusicalProduct.createMusicalProductFromResultSet(resultSet);
                return musicalProduct;
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
        throw new Exception("There is no product with such id");
    }

}
