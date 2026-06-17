package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RociadorDeAgua {

    private static final Logger log = LoggerFactory.getLogger(RociadorDeAgua.class);

    public void abrir() {
        log.info("[ROCIADOR] Rociadores de agua abiertos");
    }

    public void cerrar() {
        log.info("[ROCIADOR] Rociadores de agua cerrados");
    }
}
