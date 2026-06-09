package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta;

import java.util.List;

public record VentaCreateRequest(
	Long comercioId,
	List<ItemVentaRequest> items
) {
}
