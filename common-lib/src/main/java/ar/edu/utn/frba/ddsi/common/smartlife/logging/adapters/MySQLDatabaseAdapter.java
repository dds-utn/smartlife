package ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.connectors.MySQLConnector;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.utils.ConfigReader;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class MySQLDatabaseAdapter implements DatabaseAdapter {

    private final MySQLConnector connector;
    private final ConfigReader config;

    public MySQLDatabaseAdapter() {
        this.connector = new MySQLConnector();
        this.config = new ConfigReader();
    }

    MySQLDatabaseAdapter(MySQLConnector connector, ConfigReader config) {
        this.connector = connector;
        this.config = config;
    }

    @Override
    public void connect() {
        try {
            connector.connect(
                    config.getProperty("mysql.url"),
                    config.getProperty("mysql.username"),
                    config.getProperty("mysql.password")
            );
        } catch (IOException | SQLException e) {
            throw new RuntimeException("No se pudo conectar a MySQL", e);
        }
    }

    @Override
    public void insert(String tableOrCollection, Map<String, Object> data) {
        try {
            connector.insert(tableOrCollection, data);
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar en MySQL", e);
        }
    }

    @Override
    public void disconnect() {
        try {
            connector.disconnect();
        } catch (SQLException e) {
            System.err.println("MySQLDatabaseAdapter: error al cerrar la conexión: " + e.getMessage());
        }
    }
}
