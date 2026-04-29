package ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto;

public record ProductoTrendResponse(
	Long productoId,
	String nivel,
	String icono,
	String leyenda,
	String detalle
) {
}
