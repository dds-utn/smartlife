package ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggedError;

import java.util.List;

public class CompositeErrorLoggingStrategy implements ErrorLoggingStrategy {

    private final List<ErrorLoggingStrategy> strategies;

    public CompositeErrorLoggingStrategy(List<ErrorLoggingStrategy> strategies) {
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public void logError(LoggedError error) {
        for (ErrorLoggingStrategy strategy : strategies) {
            strategy.logError(error);
        }
    }
}
