package ar.edu.utn.frba.ddsi.common.smartlife.logging.utils;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;

import java.util.LinkedHashMap;
import java.util.Map;

public final class LoggedErrorToMapConverter {

    private LoggedErrorToMapConverter() {}

    public static Map<String, Object> toMap(LoggedError error) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("timestamp", error.timestamp());
        map.put("exceptionType", error.exceptionType());
        map.put("message", error.message());
        map.put("stackTrace", error.stackTrace());
        map.put("serviceName", error.serviceName());
        return map;
    }
}
