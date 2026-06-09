package ar.edu.utn.ba.ddsi.smartlife.trends_service.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ItemVentaEvent(
    Long productoId,
    int cantidad
) {}
