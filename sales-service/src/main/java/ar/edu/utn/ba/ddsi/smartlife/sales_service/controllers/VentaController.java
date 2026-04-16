package ar.edu.utn.ba.ddsi.smartlife.sales_service.controllers;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.VentaCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.VentaResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.services.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales-service/ventas")
public class VentaController {

	private final VentaService ventaService;

	public VentaController(VentaService ventaService) {
		this.ventaService = ventaService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VentaResponse create(@RequestBody VentaCreateRequest request) {
		return ventaService.create(request);
	}
}
