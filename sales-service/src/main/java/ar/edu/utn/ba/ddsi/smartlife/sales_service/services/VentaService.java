package ar.edu.utn.ba.ddsi.smartlife.sales_service.services;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.VentaCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.VentaResponse;

public interface VentaService {

	VentaResponse create(VentaCreateRequest request);
}
