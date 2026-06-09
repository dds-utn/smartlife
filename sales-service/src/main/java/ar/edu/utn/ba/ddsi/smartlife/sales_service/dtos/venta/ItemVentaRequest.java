package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta;

public record ItemVentaRequest(
	Long productoId,
	int cantidad
) {
}
