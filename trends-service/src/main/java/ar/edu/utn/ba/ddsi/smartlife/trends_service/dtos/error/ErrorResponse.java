package ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.error;

import java.time.Instant;

public record ErrorResponse(
	String error,
	String message,
	Instant timestamp
) {
}
