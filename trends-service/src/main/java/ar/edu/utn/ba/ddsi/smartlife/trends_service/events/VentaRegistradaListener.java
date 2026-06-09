package ar.edu.utn.ba.ddsi.smartlife.trends_service.events;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.dtos.evento.VentaRegistradaEvento;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.events.dto.ItemVentaEvent;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.events.dto.VentaRegistradaEvent;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.services.TrendProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class VentaRegistradaListener {

    private static final Logger log = LoggerFactory.getLogger(VentaRegistradaListener.class);

    private final TrendProductoService trendProductoService;

    public VentaRegistradaListener(TrendProductoService trendProductoService) {
        this.trendProductoService = trendProductoService;
    }

    @RabbitListener(queues = "${smartlife.events.queues.venta-registrada}")
    public void onVentaRegistrada(VentaRegistradaEvent event) {
        if (event == null || event.items() == null || event.items().isEmpty()) {
            log.warn("VentaRegistrada recibida sin items, se ignora. ventaId={}", event == null ? null : event.ventaId());
            return;
        }
        log.info("Procesando VentaRegistrada ventaId={} con {} items", event.ventaId(), event.items().size());
        for (ItemVentaEvent item : event.items()) {
            procesarItem(event.ventaId(), item);
        }
    }

    private void procesarItem(Long ventaId, ItemVentaEvent item) {
        try {
            trendProductoService.procesarVentaRegistrada(new VentaRegistradaEvento(item.productoId(), item.cantidad()));
        } catch (ResourceNotFoundException ex) {
            log.warn("Producto no encontrado en trends-service, se ignora item. ventaId={} productoId={}", ventaId, item.productoId());
        }
    }
}
