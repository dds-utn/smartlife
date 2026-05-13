package ar.edu.utn.frba.ddsi.common.smartlife.logging;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.DatabaseAdapter;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.adapters.DatabaseAdapterFactory;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.*;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.*;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.*;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.utils.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

public final class LoggerFactory {

    private LoggerFactory() {}

    public static Logger create() {
        ConfigReader config = new ConfigReader();
        try {
            String serviceName = config.getProperty("logging.service.name", "unknown-service");
            String destinationsRaw = config.getProperty("logging.destinations", "console");

            List<String> destinations = Arrays.stream(destinationsRaw.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .toList();

            List<ErrorLoggingStrategy> strategies = new ArrayList<>();

            for (String dest : destinations) {
                strategies.add(buildStrategy(dest, config));
            }

            ServiceLoader<ErrorLoggingStrategy> spiLoader =
                    ServiceLoader.load(ErrorLoggingStrategy.class);
            spiLoader.forEach(strategies::add);

            String extraClasses = config.getProperty("logging.strategy.extraClasses", "");
            if (!extraClasses.isBlank()) {
                for (String className : extraClasses.split(",")) {
                    strategies.add(loadStrategyByClassName(className.trim()));
                }
            }

            ErrorLoggingStrategy resolved = strategies.size() == 1
                    ? strategies.get(0)
                    : new CompositeErrorLoggingStrategy(strategies);

            return new DefaultLogger(resolved, serviceName);

        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer la configuración del logger", e);
        }
    }

    public static Logger createConsoleLogger(String serviceName) {
        return new DefaultLogger(new ConsoleErrorLoggingStrategy(), serviceName);
    }

    public static Logger createFileLogger(String serviceName, String filePath) {
        return new DefaultLogger(new FileErrorLoggingStrategy(filePath), serviceName);
    }

    public static Logger createDatabaseLogger(String serviceName, DatabaseAdapter adapter) {
        return new DefaultLogger(new DatabaseErrorLoggingStrategy(adapter), serviceName);
    }

    private static ErrorLoggingStrategy buildStrategy(String destination, ConfigReader config)
            throws IOException {
        return switch (destination.toLowerCase()) {
            case "console" -> new ConsoleErrorLoggingStrategy();
            case "file" -> {
                String path = config.getProperty("logging.file.path");
                if (path == null || path.isBlank()) {
                    throw new IllegalStateException(
                            "Se declaró el destino 'file' pero falta la propiedad 'logging.file.path'");
                }
                yield new FileErrorLoggingStrategy(path);
            }
            case "database" -> {
                String engine = config.getProperty("logging.db.engine");
                if (engine == null || engine.isBlank()) {
                    throw new IllegalStateException(
                            "Se declaró el destino 'database' pero falta la propiedad 'logging.db.engine'");
                }
                yield new DatabaseErrorLoggingStrategy(DatabaseAdapterFactory.create(engine));
            }
            default -> throw new IllegalArgumentException(
                    "Destino de logging desconocido: '" + destination + "'. " +
                    "Valores válidos: console, file, database");
        };
    }

    private static ErrorLoggingStrategy loadStrategyByClassName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return (ErrorLoggingStrategy) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("No se pudo cargar la estrategia: " + className, e);
        }
    }
}
