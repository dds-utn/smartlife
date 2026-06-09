package ar.edu.utn.frba.ddsi.common.smartlife.logging;

public interface Logger {

    void log(Exception exception);

    void log(Exception exception, String serviceName);
}
