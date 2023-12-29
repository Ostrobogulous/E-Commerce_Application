package db;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {

    private static Connection connection;

    public static Connection getDbConnection() {
        if (connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:instance/eCommerceDatabase.db");
                connection.createStatement().execute("PRAGMA foreign_keys = ON");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeDbConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initDb() {
        String databaseUrl = "jdbc:sqlite:instance/eCommerceDatabase.db";
        String sqlFilePath = "src/schema.sql";
        try {
            Class.forName("org.sqlite.JDBC");

            try (Connection connection = DriverManager.getConnection(databaseUrl)) {
                try (Statement statement = connection.createStatement()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(sqlFilePath))) {
                        String line;
                        StringBuilder query = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            query.append(line);
                            if (line.trim().endsWith(";")) {
                                statement.executeUpdate(query.toString());
                                query.setLength(0);
                            }
                        }
                    }
                    // Add PRAGMA foreign_keys = ON to enable foreign key constraint
                    statement.execute("PRAGMA foreign_keys = ON");
                }
            }
            System.out.println("Database initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        initDb();
    }
}
