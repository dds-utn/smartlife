package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AmenazaDetectadaEvent(
    Long hogarId,
    String tipo,
    String gradoRiesgo,
    LocalDateTime fechaHora,
    List<String> accionesEjecutadas
) {
}
