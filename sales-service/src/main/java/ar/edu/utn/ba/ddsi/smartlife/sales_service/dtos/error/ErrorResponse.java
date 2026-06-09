package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.error;

import java.time.Instant;

public record ErrorResponse(
	String error,
	String message,
	Instant timestamp
) {
}
