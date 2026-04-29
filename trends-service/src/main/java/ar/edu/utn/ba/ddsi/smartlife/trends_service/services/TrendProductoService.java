package ar.edu.utn.ba.ddsi.smartlife.trends_service.services;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.LeyendaResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoCreateRequest;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoTrendResponse;

public interface TrendProductoService {

	ProductoTrendResponse obtenerTendencia(Long productoId);

	LeyendaResponse obtenerLeyenda(Long productoId);

	ProductoTrendResponse crear(ProductoCreateRequest request);

	ProductoTrendResponse registrarLike(Long productoId);

	ProductoTrendResponse registrarDislike(Long productoId);

	/**
	 * Procesa el evento de dominio {@code VentaRegistrada} emitido por {@code sales-service} cuando se confirma una venta.
	 * <p>
	 * En una integración futura con el broker de eventos de SmartLife, un consumidor suscrito a dicho evento invocará este
	 * método para incrementar {@link ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto#getVentasAcumuladas()},
	 * actualizar la marca temporal de última venta y recalcular el estado de tendencia del producto.
	 * </p>
	 *
	 * @param evento datos mínimos necesarios para correlacionar la venta con el producto en este servicio
	 */
	void procesarVentaRegistrada(VentaRegistradaEvento evento);
}
