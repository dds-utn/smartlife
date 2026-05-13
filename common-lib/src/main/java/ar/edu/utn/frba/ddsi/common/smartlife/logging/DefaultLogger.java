package ar.edu.utn.frba.ddsi.common.smartlife.logging;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.strategies.ErrorLoggingStrategy;

class DefaultLogger implements Logger {

    private final ErrorLoggingStrategy strategy;
    private final String defaultServiceName;

    DefaultLogger(ErrorLoggingStrategy strategy, String defaultServiceName) {
        this.strategy = strategy;
        this.defaultServiceName = defaultServiceName;
    }

    @Override
    public void log(Exception exception) {
        log(exception, defaultServiceName);
    }

    @Override
    public void log(Exception exception, String serviceName) {
        LoggedError error = LoggedError.of(exception, serviceName);
        strategy.logError(error);
    }
}
