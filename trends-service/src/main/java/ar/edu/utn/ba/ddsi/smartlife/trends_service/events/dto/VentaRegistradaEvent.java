package ar.edu.utn.ba.ddsi.smartlife.trends_service.events.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VentaRegistradaEvent(
    Long ventaId,
    Long comercioId,
    List<ItemVentaEvent> items
) {}
