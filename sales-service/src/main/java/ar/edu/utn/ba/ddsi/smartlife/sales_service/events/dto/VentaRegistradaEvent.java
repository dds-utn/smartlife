package ar.edu.utn.ba.ddsi.smartlife.sales_service.events.dto;

import java.time.LocalDate;
import java.util.List;

public record VentaRegistradaEvent(
    Long ventaId,
    Long comercioId,
    LocalDate fechaRegistro,
    List<ItemVentaEvent> items,
    double totalPrecioBase,
    double totalImpuestos,
    double totalFinal
) {}
