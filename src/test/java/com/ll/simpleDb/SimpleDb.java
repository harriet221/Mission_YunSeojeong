package com.ll.simpleDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SimpleDb { // chatGPT
    private final String url;
    private final String username;
    private final String password;
    private final String databaseName;
    private Connection connection;

    public SimpleDb(String url, String username, String password, String databaseName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    public void setDevMode(boolean devMode) {
        // Implement this method if needed for development mode.
    }

    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String fullUrl = url + "/" + databaseName;
            connection = DriverManager.getConnection(fullUrl, username, password);
        }
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public Sql genSql() {
        return new Sql(connection);
    }

    public void run(String query, Object... params) {
        try {
            connect();
            PreparedStatement statement = connection.prepareStatement(query);
            setParameters(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        } finally {
            try {
                disconnect();
            } catch (SQLException e) {
                // Handle or log the exception
                e.printStackTrace();
            }
        }
    }

    private void setParameters(PreparedStatement statement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    // Other methods for executing SQL queries, like select, insert, update, delete, etc.
}
