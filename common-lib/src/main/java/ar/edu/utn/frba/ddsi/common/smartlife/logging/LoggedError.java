package ar.edu.utn.frba.ddsi.common.smartlife.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

public record LoggedError(
        LocalDateTime timestamp,
        String exceptionType,
        String message,
        String stackTrace,
        String serviceName
) {

    public static LoggedError of(Exception exception, String serviceName) {
        return new LoggedError(
                LocalDateTime.now(),
                exception.getClass().getName(),
                exception.getMessage() != null ? exception.getMessage() : "",
                extractStackTrace(exception),
                serviceName
        );
    }

    private static String extractStackTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        exception.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
