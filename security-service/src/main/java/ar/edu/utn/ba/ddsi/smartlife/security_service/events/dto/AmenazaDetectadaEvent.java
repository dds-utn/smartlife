package ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmenazaDetectadaEvent(
    Long hogarId,
    String tipo,
    String gradoRiesgo,
    LocalDateTime fechaHora,
    List<String> accionesEjecutadas
) {
}
