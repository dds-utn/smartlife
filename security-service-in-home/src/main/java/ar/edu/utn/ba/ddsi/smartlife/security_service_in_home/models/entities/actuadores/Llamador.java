package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Llamador {

    private static final Logger log = LoggerFactory.getLogger(Llamador.class);

    public void llamar(String numero, String mensaje) {
        log.info("[LLAMADOR] Llamando al {} con mensaje: {}", numero, mensaje);
    }
}
