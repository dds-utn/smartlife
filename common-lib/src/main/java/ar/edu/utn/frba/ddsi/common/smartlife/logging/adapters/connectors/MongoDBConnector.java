package ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.connectors;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnector {

    private MongoClient client;
    private MongoDatabase database;

    public void connect(String connectionString, String databaseName) {
        client = MongoClients.create(connectionString);
        database = client.getDatabase(databaseName);
    }

    public void disconnect() {
        if (client != null) {
            client.close();
        }
    }

    public void insert(String collectionName, Document document) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.insertOne(document);
    }
}
