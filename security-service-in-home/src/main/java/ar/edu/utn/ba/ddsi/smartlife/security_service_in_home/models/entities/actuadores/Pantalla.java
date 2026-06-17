package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pantalla {

    private static final Logger log = LoggerFactory.getLogger(Pantalla.class);

    public void mostrar(String mensaje) {
        log.info("[PANTALLA CENTRAL] {}", mensaje);
    }
}
