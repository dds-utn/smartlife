package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatoSensorEvent(String tipo, String dato) {
}
