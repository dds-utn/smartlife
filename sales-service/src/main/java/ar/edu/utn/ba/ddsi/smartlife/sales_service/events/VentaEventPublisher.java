package ar.edu.utn.ba.ddsi.smartlife.sales_service.events;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.events.dto.ItemVentaEvent;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.events.dto.VentaRegistradaEvent;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta.ItemVenta;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.venta.Venta;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VentaEventPublisher {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(VentaEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public VentaEventPublisher(
        RabbitTemplate rabbitTemplate,
        @Value("${smartlife.events.exchange}") String exchange,
        @Value("${smartlife.events.routing-keys.venta-registrada}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void publicarVentaRegistrada(Comercio comercio, Venta venta) {
        VentaRegistradaEvent event = toEvent(comercio, venta);
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, event);
            log.info("Evento VentaRegistrada publicado: ventaId={} comercioId={}", event.ventaId(), event.comercioId());
        } catch (AmqpException ex) {
            log.error("Error publicando VentaRegistrada para ventaId={}: {}", event.ventaId(), ex.getMessage(), ex);
        }
    }

    private VentaRegistradaEvent toEvent(Comercio comercio, Venta venta) {
        List<ItemVentaEvent> items = venta.getItems().stream()
            .map(this::toItemEvent)
            .toList();
        return new VentaRegistradaEvent(
            venta.getId(),
            comercio.getId(),
            venta.getFechaRegistro(),
            items,
            venta.totalPrecioBase(),
            venta.totalImpuestos(),
            venta.totalFinal()
        );
    }

    private ItemVentaEvent toItemEvent(ItemVenta item) {
        return new ItemVentaEvent(
            item.getProducto().getId(),
            item.getCantidad(),
            item.getProducto().getPrecioBase(),
            item.totalImpuestos(),
            item.totalFinal()
        );
    }
}
