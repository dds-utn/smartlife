package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto;

public record ProductoResponse(
	Long id,
	Long comercioId,
	Long tipoProductoId,
	String tipoProductoDescripcion,
	double precioBase,
	String descripcion
) {
}
