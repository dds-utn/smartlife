package ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;

import java.time.format.DateTimeFormatter;

public class ConsoleErrorLoggingStrategy implements ErrorLoggingStrategy {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void logError(LoggedError error) {
        System.out.printf(
                "[%s] [%s] [service=%s] %s%n%s%n",
                error.timestamp().format(FORMATTER),
                error.exceptionType(),
                error.serviceName(),
                error.message(),
                error.stackTrace()
        );
    }
}
