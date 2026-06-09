package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto;

public record ProductoCreateRequest(
	Long comercioId,
	Long tipoProductoId,
	double precioBase,
	String descripcion
) {
}
