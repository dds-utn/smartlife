package ar.edu.utn.ba.ddsi.smartlife.sales_service.services.impl;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.PrecioProductoResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.producto.ProductoUpdateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.Producto;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.TipoProducto;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.TipoProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.services.ProductoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

	private final ProductoRepository productoRepository;
	private final ComercioRepository comercioRepository;
	private final TipoProductoRepository tipoProductoRepository;

	public ProductoServiceImpl(ProductoRepository productoRepository,
						   ComercioRepository comercioRepository,
						   TipoProductoRepository tipoProductoRepository) {
		this.productoRepository = productoRepository;
		this.comercioRepository = comercioRepository;
		this.tipoProductoRepository = tipoProductoRepository;
	}

	@Override
	public List<ProductoResponse> findAll() {
		return productoRepository.findAll().stream().map(this::toResponse).toList();
	}

	@Override
	public ProductoResponse findById(Long id) {
		return toResponse(getProductoOrThrow(id));
	}

	@Override
	public ProductoResponse create(ProductoCreateRequest request) {
		validateCreateRequest(request);
		Comercio comercio = getComercioOrThrow(request.comercioId());
		TipoProducto tipo = getTipoOrThrow(request.tipoProductoId());
		Producto producto = new Producto(null, tipo, request.precioBase(), request.descripcion().trim());
		comercio.agregarProducto(producto);
		productoRepository.save(producto);
		return toResponse(producto, comercio);
	}

	@Override
	public ProductoResponse update(ProductoUpdateRequest request) {
		validateUpdateRequest(request);
		Producto producto = getProductoOrThrow(request.id());
		Comercio comercio = getComercioOrThrow(request.comercioId());
		TipoProducto tipo = getTipoOrThrow(request.tipoProductoId());

		if (!comercio.getProductos().contains(producto)) {
			throw new BusinessException("El producto no pertenece al comercio indicado");
		}

		producto.setTipo(tipo);
		producto.setPrecioBase(request.precioBase());
		producto.setDescripcion(request.descripcion().trim());
		productoRepository.save(producto);
		return toResponse(producto, comercio);
	}

	@Override
	public void deleteById(Long id) {
		Producto producto = getProductoOrThrow(id);
		Comercio comercio = findComercioOwner(producto);
		comercio.getProductos().remove(producto);
		productoRepository.delete(producto);
	}

	@Override
	public PrecioProductoResponse getPrecio(Long id) {
		Producto producto = getProductoOrThrow(id);
		return new PrecioProductoResponse(producto.getId(), producto.getPrecioBase(), producto.totalImpuestos(), producto.precioFinal());
	}

	private ProductoResponse toResponse(Producto producto) {
		Comercio comercio = findComercioOwner(producto);
		return toResponse(producto, comercio);
	}

	private ProductoResponse toResponse(Producto producto, Comercio comercio) {
		return new ProductoResponse(
			producto.getId(),
			comercio.getId(),
			producto.getTipo().getId(),
			producto.getTipo().getDescripcion(),
			producto.getPrecioBase(),
			producto.getDescripcion()
		);
	}

	private Comercio findComercioOwner(Producto producto) {
		return comercioRepository.findAll().stream()
			.filter(c -> c.getProductos().contains(producto))
			.findFirst()
			.orElseThrow(() -> new ResourceNotFoundException("No se encontró comercio para el producto " + producto.getId()));
	}

	private void validateCreateRequest(ProductoCreateRequest request) {
		if (request == null) throw new BusinessException("El body del producto es obligatorio");
		if (request.comercioId() == null) throw new BusinessException("El comercioId es obligatorio");
		if (request.tipoProductoId() == null) throw new BusinessException("El tipoProductoId es obligatorio");
		validateCommonFields(request.precioBase(), request.descripcion());
	}

	private void validateUpdateRequest(ProductoUpdateRequest request) {
		if (request == null) throw new BusinessException("El body del producto es obligatorio");
		if (request.id() == null) throw new BusinessException("El id del producto es obligatorio");
		if (request.comercioId() == null) throw new BusinessException("El comercioId es obligatorio");
		if (request.tipoProductoId() == null) throw new BusinessException("El tipoProductoId es obligatorio");
		validateCommonFields(request.precioBase(), request.descripcion());
	}

	private void validateCommonFields(double precioBase, String descripcion) {
		if (descripcion == null || descripcion.isBlank()) throw new BusinessException("La descripcion del producto es obligatoria");
		if (precioBase <= 0) throw new BusinessException("El precio base debe ser mayor a cero");
	}

	private Comercio getComercioOrThrow(Long comercioId) {
		return comercioRepository.findById(comercioId)
			.orElseThrow(() -> new ResourceNotFoundException("No se encontró comercio con id " + comercioId));
	}

	private Producto getProductoOrThrow(Long productoId) {
		return productoRepository.findById(productoId)
			.orElseThrow(() -> new ResourceNotFoundException("No se encontró producto con id " + productoId));
	}

	private TipoProducto getTipoOrThrow(Long tipoProductoId) {
		return tipoProductoRepository.findById(tipoProductoId)
			.orElseThrow(() -> new ResourceNotFoundException("No se encontró tipo de producto con id " + tipoProductoId));
	}
}
