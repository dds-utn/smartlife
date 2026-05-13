package ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;

public interface ErrorLoggingStrategy {
    void logError(LoggedError error);
}
