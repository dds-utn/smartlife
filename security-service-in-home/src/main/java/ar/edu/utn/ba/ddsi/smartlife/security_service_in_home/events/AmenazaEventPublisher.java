package ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events;

import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.events.dto.AmenazaDetectadaEvent;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Evento;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service_in_home.models.entities.acciones.AmenazaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AmenazaEventPublisher implements AmenazaListener {

    private static final Logger log = LoggerFactory.getLogger(AmenazaEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public AmenazaEventPublisher(
        RabbitTemplate rabbitTemplate,
        @Value("${smartlife.events.exchange}") String exchange,
        @Value("${smartlife.events.routing-keys.amenaza-detectada}") String routingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void onAmenazaDetectada(Evento evento) {
        AmenazaDetectadaEvent event = new AmenazaDetectadaEvent(
            Hogar.getInstance().getId(),
            evento.getTipo().name(),
            evento.getGradoRiesgo() == null ? null : evento.getGradoRiesgo().name(),
            evento.getFechaHora(),
            evento.getAccionesEjecutadas()
        );
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, event);
            log.info("Evento AmenazaDetectada publicado: hogarId={} tipo={} gradoRiesgo={}",
                event.hogarId(), event.tipo(), event.gradoRiesgo());
        } catch (AmqpException ex) {
            log.error("Error publicando AmenazaDetectada para hogarId={}: {}",
                event.hogarId(), ex.getMessage(), ex);
        }
    }
}
