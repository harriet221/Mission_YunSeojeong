package com.ll.simpleDb;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Sql { // chatGPT
    private final Connection connection;
    private PreparedStatement statement;
    private String query;
    private List<Object> parameters;

    public Sql(Connection connection) {
        this.connection = connection;
        this.parameters = new ArrayList<>();
    }

    public Sql append(String text) {
        if (query == null) {
            query = text;
        } else {
            query += " " + text;
        }
        return this;
    }

    public Sql append(String text, Object... params) {
        append(text);
        for (Object param : params) {
            parameters.add(param);
        }
        return this;
    }

    public Sql appendIn(String text, List<?> values) {
        if (!values.isEmpty()) {
            StringBuilder inClause = new StringBuilder("(");
            for (int i = 0; i < values.size(); i++) {
                inClause.append("?");
                if (i < values.size() - 1) {
                    inClause.append(",");
                }
                parameters.add(values.get(i));
            }
            inClause.append(")");
            append(text.replace("?", inClause.toString()));
        }
        return this;
    }

    public long insert() {
        try {
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            setParameters(statement, parameters.toArray());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
        return -1;
    }

    public long update() {
        try {
            statement = connection.prepareStatement(query);
            setParameters(statement, parameters.toArray());
            return statement.executeUpdate();
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
            return 0;
        }
    }

    public long delete() {
        return update(); // Deletion is just a special case of an update operation
    }

    public LocalDateTime selectDatetime() {
        try {
            statement = connection.prepareStatement(query);
            setParameters(statement, parameters.toArray());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getTimestamp(1).toLocalDateTime();
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
        return null;
    }

    public Long selectLong() {
        try {
            statement = connection.prepareStatement(query);
            setParameters(statement, parameters.toArray());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
        return null;
    }

    public String selectString() {
        try {
            statement = connection.prepareStatement(query);
            setParameters(statement, parameters.toArray());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> selectRow() {
        // Implement this method to return a row as a map
        return null;
    }

    public <T> T selectRow(Class<T> clazz) {
        // Implement this method to return a row as an instance of the given class
        return null;
    }

    public <T> List<T> selectRows(Class<T> clazz) {
        // Implement this method to return multiple rows as a list of instances of the given class
        return null;
    }

    public List<Long> selectLongs() {
        // Implement this method to return a list of long values
        return null;
    }

    private void setParameters(PreparedStatement statement, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }
}
