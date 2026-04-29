package ar.edu.utn.ba.ddsi.smartlife.trends_service.services.impl;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.LeyendaResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoTrendResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import org.springframework.stereotype.Service;

@Service
public class TrendProductoServiceImpl implements TrendProductoService {

	private final ProductoRepository productoRepository;
	private final ComercioRepository comercioRepository;

	public TrendProductoServiceImpl(ProductoRepository productoRepository, ComercioRepository comercioRepository) {
		this.productoRepository = productoRepository;
		this.comercioRepository = comercioRepository;
	}

	@Override
	public ProductoTrendResponse obtenerTendencia(Long productoId) {
		Producto producto = obtenerProducto(productoId);
		return mapear(producto);
	}

	@Override
	public LeyendaResponse obtenerLeyenda(Long productoId) {
		Producto producto = obtenerProducto(productoId);
		productoRepository.save(producto);
		return new LeyendaResponse(producto.leyenda());
	}

	@Override
	public ProductoTrendResponse crear(ProductoCreateRequest request) {
		validarCreacion(request);
		Comercio comercio = obtenerComercio(request.comercioId());
		Producto producto = new Producto();
		producto.setComercio(comercio);
		producto.setNombre(request.nombre().trim());
		producto.setCategoria(request.categoria().trim());
		producto.setPrecioBase(request.precioBase());
		Producto guardado = productoRepository.save(producto);
		return mapear(guardado);
	}

	@Override
	public ProductoTrendResponse registrarLike(Long productoId) {
		Producto producto = obtenerProducto(productoId);
		producto.recibirLike();
		productoRepository.save(producto);
		return mapear(producto);
	}

	@Override
	public ProductoTrendResponse registrarDislike(Long productoId) {
		Producto producto = obtenerProducto(productoId);
		producto.recibirDislike();
		productoRepository.save(producto);
		return mapear(producto);
	}

	@Override
	public void procesarVentaRegistrada(VentaRegistradaEvento evento) {
		if (evento.cantidadVendida() <= 0) {
			throw new BusinessException("La cantidad vendida debe ser mayor a cero");
		}
		Producto producto = obtenerProducto(evento.productoId());
		producto.registrarVenta(evento.cantidadVendida());
		productoRepository.save(producto);
	}

	private ProductoTrendResponse mapear(Producto producto) {
		productoRepository.save(producto);
		return new ProductoTrendResponse(
			producto.getId(),
			producto.etiqueta(),
			producto.iconoTexto(),
			producto.leyenda(),
			producto.detalle()
		);
	}

	private void validarCreacion(ProductoCreateRequest request) {
		if (request.comercioId() == null) {
			throw new BusinessException("comercioId es obligatorio");
		}
		if (request.nombre() == null || request.nombre().isBlank()) {
			throw new BusinessException("nombre es obligatorio");
		}
		if (request.categoria() == null || request.categoria().isBlank()) {
			throw new BusinessException("categoria es obligatorio");
		}
		if (request.precioBase() < 0) {
			throw new BusinessException("precioBase no puede ser negativo");
		}
	}

	private Producto obtenerProducto(Long productoId) {
		return productoRepository.findById(productoId)
			.orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
	}

	private Comercio obtenerComercio(Long comercioId) {
		return comercioRepository.findById(comercioId)
			.orElseThrow(() -> new ResourceNotFoundException("Comercio no encontrado"));
	}
}
