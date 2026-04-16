package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto;

public record PrecioProductoResponse(
	Long productoId,
	double precioBase,
	double impuestos,
	double precioFinal
) {
}
