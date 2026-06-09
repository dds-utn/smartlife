package ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.connectors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.StringJoiner;

public class MySQLConnector {

    private Connection connection;

    public void connect(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void insert(String tableName, Map<String, Object> data) throws SQLException {
        String[] columns = data.keySet().toArray(new String[0]);
        Object[] values = data.values().toArray();

        StringJoiner colJoiner = new StringJoiner(", ");
        StringJoiner placeholders = new StringJoiner(", ");
        for (String col : columns) {
            colJoiner.add(col);
            placeholders.add("?");
        }

        String sql = "INSERT INTO " + tableName + " (" + colJoiner + ") VALUES (" + placeholders + ")";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                stmt.setObject(i + 1, values[i]);
            }
            stmt.executeUpdate();
        }
    }
}
