package ar.edu.utn.ba.ddsi.smartlife.sales_service.services.impl;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.ItemVentaRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.VentaCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.dtos.venta.VentaResponse;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.Producto;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta.ItemVenta;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta.Venta;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ProductoRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.VentaRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.services.VentaService;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl implements VentaService {

	private final ComercioRepository comercioRepository;
	private final ProductoRepository productoRepository;
	private final VentaRepository ventaRepository;

	public VentaServiceImpl(ComercioRepository comercioRepository,
					   ProductoRepository productoRepository,
					   VentaRepository ventaRepository) {
		this.comercioRepository = comercioRepository;
		this.productoRepository = productoRepository;
		this.ventaRepository = ventaRepository;
	}

	@Override
	public VentaResponse create(VentaCreateRequest request) {
		validateRequest(request);
		Comercio comercio = comercioRepository.findById(request.comercioId())
			.orElseThrow(() -> new ResourceNotFoundException("No se encontró comercio con id " + request.comercioId()));

		Venta venta = new Venta(null);
		for (ItemVentaRequest itemRequest : request.items()) {
			Producto producto = productoRepository.findById(itemRequest.productoId())
				.orElseThrow(() -> new ResourceNotFoundException("No se encontró producto con id " + itemRequest.productoId()));
			if (!comercio.getProductos().contains(producto)) {
				throw new BusinessException("Todos los productos de la venta deben pertenecer al comercio indicado");
			}
			venta.agregarItem(new ItemVenta(producto, itemRequest.cantidad()));
		}

		try {
			comercio.agregarVenta(venta);
		} catch (IllegalArgumentException ex) {
			throw new BusinessException(ex.getMessage());
		}
		ventaRepository.save(venta);

		return new VentaResponse(
			venta.getId(),
			comercio.getId(),
			venta.getFechaRegistro(),
			venta.totalPrecioBase(),
			venta.totalImpuestos(),
			venta.totalFinal()
		);
	}

	private void validateRequest(VentaCreateRequest request) {
		if (request == null) throw new BusinessException("El body de la venta es obligatorio");
		if (request.comercioId() == null) throw new BusinessException("El comercioId es obligatorio");
		if (request.items() == null || request.items().isEmpty()) throw new BusinessException("La venta debe incluir al menos un item");
		for (ItemVentaRequest item : request.items()) {
			if (item.productoId() == null) throw new BusinessException("Cada item debe incluir productoId");
			if (item.cantidad() <= 0) throw new BusinessException("La cantidad de cada item debe ser mayor a cero");
		}
	}
}
