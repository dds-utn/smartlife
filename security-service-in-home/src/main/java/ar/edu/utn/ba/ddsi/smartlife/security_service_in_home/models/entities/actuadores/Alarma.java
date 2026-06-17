package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Alarma {

    private static final Logger log = LoggerFactory.getLogger(Alarma.class);

    public void sonar() {
        log.info("[ALARMA] La alarma del hogar está sonando");
    }
}
