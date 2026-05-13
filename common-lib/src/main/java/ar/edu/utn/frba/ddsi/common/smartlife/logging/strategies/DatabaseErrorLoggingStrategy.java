package ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.DatabaseAdapter;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.utils.LoggedErrorToMapConverter;

public class DatabaseErrorLoggingStrategy implements ErrorLoggingStrategy {

    public static final String TABLE_OR_COLLECTION = "error_log";

    private final DatabaseAdapter adapter;

    public DatabaseErrorLoggingStrategy(DatabaseAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void logError(LoggedError error) {
        adapter.connect();
        adapter.insert(TABLE_OR_COLLECTION, LoggedErrorToMapConverter.toMap(error));
        adapter.disconnect();
    }
}
