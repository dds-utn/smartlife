package ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta;

import java.time.LocalDate;

public record VentaResponse(
	Long id,
	Long comercioId,
	LocalDate fechaRegistro,
	double totalPrecioBase,
	double totalImpuestos,
	double totalFinal
) {
}
