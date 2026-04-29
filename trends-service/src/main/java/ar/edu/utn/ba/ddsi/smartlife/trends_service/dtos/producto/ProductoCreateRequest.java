package ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto;

public record ProductoCreateRequest(
	Long comercioId,
	String nombre,
	String categoria,
	double precioBase
) {
}
