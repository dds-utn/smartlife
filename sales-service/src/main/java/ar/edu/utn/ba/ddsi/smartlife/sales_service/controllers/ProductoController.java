package ar.edu.utn.ba.ddsi.smartlife.sales_service.controllers;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.PrecioProductoResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoUpdateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sales-service/productos")
public class ProductoController {

	private final ProductoService productoService;

	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@GetMapping
	public List<ProductoResponse> getAll() {
		return productoService.findAll();
	}

	@GetMapping("/{id}")
	public ProductoResponse getById(@PathVariable Long id) {
		return productoService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductoResponse create(@RequestBody ProductoCreateRequest request) {
		return productoService.create(request);
	}

	@PutMapping
	public ProductoResponse update(@RequestBody ProductoUpdateRequest request) {
		return productoService.update(request);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		productoService.deleteById(id);
	}

	@GetMapping("/{id}/precio")
	public PrecioProductoResponse getPrecio(@PathVariable Long id) {
		return productoService.getPrecio(id);
	}
}
