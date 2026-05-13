package ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.connectors.MongoDBConnector;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.utils.ConfigReader;
import org.bson.Document;

import java.io.IOException;
import java.util.Map;

public class MongoDBDatabaseAdapter implements DatabaseAdapter {

    private final MongoDBConnector connector;
    private final ConfigReader config;

    public MongoDBDatabaseAdapter() {
        this.connector = new MongoDBConnector();
        this.config = new ConfigReader();
    }

    MongoDBDatabaseAdapter(MongoDBConnector connector, ConfigReader config) {
        this.connector = connector;
        this.config = config;
    }

    @Override
    public void connect() {
        try {
            connector.connect(
                    config.getProperty("mongodb.connectionString"),
                    config.getProperty("mongodb.databaseName")
            );
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer la configuración de MongoDB", e);
        }
    }

    @Override
    public void insert(String tableOrCollection, Map<String, Object> data) {
        Document document = new Document();
        data.forEach(document::append);
        connector.insert(tableOrCollection, document);
    }

    @Override
    public void disconnect() {
        connector.disconnect();
    }
}
