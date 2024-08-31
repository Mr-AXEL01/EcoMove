package net.axel.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    private static final String URL = "jdbc:postgresql://localhost:5432/ecomove";
    private static final String USER = "postgres";
    private static final String PASSWORD = "6631";

    private DatabaseConnection() { }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DatabaseConnection.class) {
                if (connection == null) {
                    try {
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                        System.out.println("Connected to the PostgreSQL server successfully.");
                    } catch (SQLException e) {
                        System.out.println("Failed to connect to the PostgreSQL server: " + e.getMessage());
                    }
                }
            }
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null; // Reset the connection
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }
}
