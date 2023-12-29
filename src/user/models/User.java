package user.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int id, String firstName, String lastName, String email, String password, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public static User createInstance(Map<String, String> data) {
        return new User(data.get("firstName"), data.get("lastName"),
                data.get("email"), data.get("password"), data.get("role"));
    }

    /**
     * Saves the user data to the database.
     */
    public void save() {
        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            connection.setAutoCommit(false);
            String insertUserQuery = "INSERT INTO user (first_name, last_name, email, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement userStatement = connection.prepareStatement(insertUserQuery);
            userStatement.setString(1, getFirstName());
            userStatement.setString(2, getLastName());
            userStatement.setString(3, getEmail());
            userStatement.setString(4, getPassword());
            userStatement.setString(5, getRole());
            userStatement.executeUpdate();
            connection.commit();
            String query = "SELECT last_insert_rowid() FROM user";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            int lastInsertedId = resultSet.getInt(1);
            this.id = lastInsertedId;
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

    /**
     * Retrieves a user from the database based on the provided email.
     */
    public static User get(String email) throws Exception {
        Connection connection = db.DatabaseUtils.getDbConnection();
        try {
            String query = "SELECT * FROM user WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = createUserFromResultSet(resultSet);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new Exception("User with this email not found");
    }

    /**
     * Creates a User instance from the result set obtained from the database.
     */
    private static User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"),
                resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("role"));
    }
}
