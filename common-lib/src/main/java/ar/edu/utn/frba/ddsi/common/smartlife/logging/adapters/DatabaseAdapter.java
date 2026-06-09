package ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters;

import java.util.Map;

public interface DatabaseAdapter {

    void connect();

    void insert(String tableOrCollection, Map<String, Object> data);

    void disconnect();
}
