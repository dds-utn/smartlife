package ar.edu.utn.ba.ddsi.smartlife.sales_service.events.dto;

public record ItemVentaEvent(
    Long productoId,
    int cantidad,
    double precioBaseUnitario,
    double totalImpuestos,
    double totalFinal
) {}
