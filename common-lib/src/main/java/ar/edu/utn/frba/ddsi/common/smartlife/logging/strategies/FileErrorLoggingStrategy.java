package ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;

public class FileErrorLoggingStrategy implements ErrorLoggingStrategy {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String SEPARATOR =
            "=".repeat(80) + System.lineSeparator();

    private final Path filePath;

    public FileErrorLoggingStrategy(String filePath) {
        this.filePath = Path.of(filePath);
    }

    @Override
    public void logError(LoggedError error) {
        String entry = SEPARATOR +
                String.format("timestamp   : %s%n", error.timestamp().format(FORMATTER)) +
                String.format("service     : %s%n", error.serviceName()) +
                String.format("type        : %s%n", error.exceptionType()) +
                String.format("message     : %s%n", error.message()) +
                String.format("stack trace :%n%s%n", error.stackTrace());

        try {
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }
            Files.writeString(
                    filePath,
                    entry,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            System.err.printf("FileErrorLoggingStrategy: no se pudo escribir en '%s': %s%n",
                    filePath, e.getMessage());
        }
    }
}
