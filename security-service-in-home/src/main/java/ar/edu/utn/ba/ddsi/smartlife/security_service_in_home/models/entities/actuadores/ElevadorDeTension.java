package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.actuadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevadorDeTension {

    private static final Logger log = LoggerFactory.getLogger(ElevadorDeTension.class);

    public void elevarTension() {
        log.info("[ELEVADOR DE TENSION] Elevando la tensión entrante a niveles normales");
    }
}
