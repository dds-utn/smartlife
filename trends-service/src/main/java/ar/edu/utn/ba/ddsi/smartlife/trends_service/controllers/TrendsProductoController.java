package ar.edu.utn.ba.ddsi.smartlife.trends_service.controllers;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.LeyendaResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoTrendResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trends-service/trends/productos")
public class TrendsProductoController {

	private final TrendProductoService trendProductoService;

	public TrendsProductoController(TrendProductoService trendProductoService) {
		this.trendProductoService = trendProductoService;
	}

	@GetMapping("/{id}")
	public ProductoTrendResponse obtenerPorId(@PathVariable Long id) {
		return trendProductoService.obtenerTendencia(id);
	}

	@GetMapping("/{id}/leyenda")
	public LeyendaResponse leyenda(@PathVariable Long id) {
		return trendProductoService.obtenerLeyenda(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductoTrendResponse crear(@RequestBody ProductoCreateRequest request) {
		return trendProductoService.crear(request);
	}

	@PostMapping("/{id}/likes")
	public ProductoTrendResponse like(@PathVariable Long id) {
		return trendProductoService.registrarLike(id);
	}

	@PostMapping("/{id}/dislikes")
	public ProductoTrendResponse dislike(@PathVariable Long id) {
		return trendProductoService.registrarDislike(id);
	}
}
