package ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters;

public final class DatabaseAdapterFactory {

    private DatabaseAdapterFactory() {}

    public static DatabaseAdapter create(String engine) {
        return switch (engine.trim().toLowerCase()) {
            case "mongodb" -> new MongoDBDatabaseAdapter();
            case "mysql"   -> new MySQLDatabaseAdapter();
            default -> throw new IllegalArgumentException(
                    "Motor de base de datos no soportado: '" + engine + "'. " +
                    "Valores válidos: mongodb, mysql");
        };
    }
}
