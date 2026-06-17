package ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto;

import java.time.LocalDateTime;

public record HogarSeguroEvent(
    Long hogarId,
    LocalDateTime fechaHora
) {
}
