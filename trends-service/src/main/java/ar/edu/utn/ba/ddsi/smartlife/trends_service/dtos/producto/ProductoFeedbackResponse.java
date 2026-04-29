package ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto;

public record ProductoFeedbackResponse(
	Long productoId,
	long likesTotales,
	long dislikesTotales
) {
}
