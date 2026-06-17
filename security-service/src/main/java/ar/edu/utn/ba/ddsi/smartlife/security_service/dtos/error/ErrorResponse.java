package ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.error;

import java.time.Instant;

public record ErrorResponse(
    String error,
    String message,
    Instant timestamp
) {
}
