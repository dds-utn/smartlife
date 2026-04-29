package ar.edu.utn.ba.ddsi.smartlife.trends_service.controllers;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoFeedbackResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoTrendResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trends-service/trends/productos")
public class TrendsProductoController {

	private final TrendProductoService trendProductoService;

	public TrendsProductoController(TrendProductoService trendProductoService) {
		this.trendProductoService = trendProductoService;
	}

	@GetMapping("/{id}")
	public ProductoTrendResponse obtenerPorId(@PathVariable Long id) {
		return trendProductoService.buscarPorId(id);
	}

    @GetMapping
    public List<ProductoTrendResponse> obtenerTodosProductos() {
        return trendProductoService.buscarTodos();
    }

	@PostMapping("/{id}/likes")
	public ProductoFeedbackResponse like(@PathVariable Long id) {
		return trendProductoService.registrarLike(id);
	}

	@PostMapping("/{id}/dislikes")
	public ProductoFeedbackResponse dislike(@PathVariable Long id) {
		return trendProductoService.registrarDislike(id);
	}

	@PostMapping("/ventas/test")
	public ProductoTrendResponse registrarVentaTest(@RequestBody VentaRegistradaEvento evento) {
		trendProductoService.procesarVentaRegistrada(evento);
		return trendProductoService.buscarPorId(evento.productoId());
	}
}
