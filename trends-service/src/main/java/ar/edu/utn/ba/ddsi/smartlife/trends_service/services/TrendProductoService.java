package ar.edu.utn.ba.ddsi.smartlife.trends_service.services;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoFeedbackResponse;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.producto.ProductoTrendResponse;

import java.util.List;

public interface TrendProductoService {

    ProductoTrendResponse buscarPorId(Long idProducto);

    List<ProductoTrendResponse> buscarTodos();

	ProductoFeedbackResponse registrarLike(Long productoId);

	ProductoFeedbackResponse registrarDislike(Long productoId);

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

	/**
	 * Evalúa si los productos actualmente en estado {@code EnTendencia} deben volver a {@code Normal} por no haber
	 * registrado ventas en las últimas 24 horas. Invocado periódicamente por el scheduler de tendencias.
	 */
	void evaluarTransicionesDeEstadoPorTiempo();
}
