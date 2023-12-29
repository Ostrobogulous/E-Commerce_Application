package product.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BookProduct extends Product {
    private String author;
    private String genre;
    private int year;
    private int pageCount;

    public BookProduct(String name, double price, int stock, int userId, String author, String genre, int year, int pageCount) {
        super(name, price, stock, userId);
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.pageCount = pageCount;
    }

    public BookProduct(int id, String name, double price, int stock, int userId, String author, String genre, int year, int pageCount) {
        super(id, name, price, stock, userId);
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getCategory() {
        return "Book Product";
    }

    public void update(Map<String, String> data) {
        super.update(data);
        if (data.containsKey("author")) {
            setAuthor(data.get("author"));
        }
        if (data.containsKey("genre")) {
            setGenre(data.get("genre"));
        }
        if (data.containsKey("year")) {
            setYear(Integer.parseInt(data.get("year")));
        }
    }

    public void displayInfo() {
        System.out.printf("Book: %s | ID: %d | %.2f $ | Author: %s | Genre: %s | Page Count: %d\n", getName(), getId(), getPrice(), getAuthor(), getGenre(), getPageCount());
    }

    public static BookProduct createInstance(Map<String, String> data) {
        return new BookProduct(data.get("name"), Float.parseFloat(data.get("price")),
                Integer.parseInt(data.get("stock")), Integer.parseInt(data.get("userId")),
                data.get("author"), data.get("genre"), Integer.parseInt(data.get("year")),
                Integer.parseInt(data.get("pageCount")));
    }

    public void save() {
        int id = getId();
        super.save();

        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);

            if (id == 0) {
                String insertBookProductQuery = "INSERT INTO bookProduct (product_id, author, genre, year, page_count) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement statement = connection.prepareStatement(insertBookProductQuery);
                statement.setInt(1, getId());
                statement.setString(2, getAuthor());
                statement.setString(3, getGenre());
                statement.setInt(4, getYear());
                statement.setInt(5, getPageCount());

                statement.executeUpdate();
            } else {
                String updateBookProductQuery = "UPDATE bookProduct SET author = ?, genre = ?, year = ?, page_count = ? WHERE product_id = ?";
                PreparedStatement statement = connection.prepareStatement(updateBookProductQuery);
                statement.setString(1, getAuthor());
                statement.setString(2, getGenre());
                statement.setInt(3, getYear());
                statement.setInt(4, getPageCount());
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

    public static List<Product> list() {
        List<Product> bookProducts = new ArrayList<>();
        Connection connection = db.DatabaseUtils.getDbConnection();

        try {
            String retrieveQuery = "SELECT product.*, bookProduct.* " +
                    "FROM product " +
                    "JOIN bookProduct ON product.id = bookProduct.product_id";
            PreparedStatement statement = connection.prepareStatement(retrieveQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookProduct bookProduct = createBookProductFromResultSet(resultSet);
                bookProducts.add(bookProduct);
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
        bookProducts.sort(Comparator.comparingInt(Product::getId));
        return bookProducts;
    }

    public static BookProduct createBookProductFromResultSet(ResultSet resultSet) throws SQLException {
        return new BookProduct(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getFloat("price"),
                resultSet.getInt("stock"), resultSet.getInt("user_id"),
                resultSet.getString("author"), resultSet.getString("genre"),
                resultSet.getInt("year"), resultSet.getInt("page_count"));
    }
}
