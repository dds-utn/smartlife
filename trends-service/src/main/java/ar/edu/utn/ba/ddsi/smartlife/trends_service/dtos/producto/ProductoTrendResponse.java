package ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto;

public record ProductoTrendResponse(
	Long productoId,
	String detalle,
    int likes,
    int dislikes,
    int cantVentas
) {
}
