package ar.edu.utn.ba.ddsi.smartlife.security_service.events;

import ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto.HogarEnPeligroEvent;
import ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto.HogarSeguroEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HogarEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(HogarEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String enPeligroRoutingKey;
    private final String seguroRoutingKey;

    public HogarEventPublisher(
        RabbitTemplate rabbitTemplate,
        @Value("${smartlife.events.exchange}") String exchange,
        @Value("${smartlife.events.routing-keys.hogar-en-peligro}") String enPeligroRoutingKey,
        @Value("${smartlife.events.routing-keys.hogar-seguro}") String seguroRoutingKey
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.enPeligroRoutingKey = enPeligroRoutingKey;
        this.seguroRoutingKey = seguroRoutingKey;
    }

    public void publicarHogarEnPeligro(Long hogarId, String tipoAmenaza) {
        HogarEnPeligroEvent event = new HogarEnPeligroEvent(hogarId, tipoAmenaza, LocalDateTime.now());
        try {
            rabbitTemplate.convertAndSend(exchange, enPeligroRoutingKey, event);
            log.info("Evento HogarEnPeligro publicado: hogarId={} tipoAmenaza={}", hogarId, tipoAmenaza);
        } catch (AmqpException ex) {
            log.error("Error publicando HogarEnPeligro para hogarId={}: {}", hogarId, ex.getMessage(), ex);
        }
    }

    public void publicarHogarSeguro(Long hogarId) {
        HogarSeguroEvent event = new HogarSeguroEvent(hogarId, LocalDateTime.now());
        try {
            rabbitTemplate.convertAndSend(exchange, seguroRoutingKey, event);
            log.info("Evento HogarSeguro publicado: hogarId={}", hogarId);
        } catch (AmqpException ex) {
            log.error("Error publicando HogarSeguro para hogarId={}: {}", hogarId, ex.getMessage(), ex);
        }
    }
}
