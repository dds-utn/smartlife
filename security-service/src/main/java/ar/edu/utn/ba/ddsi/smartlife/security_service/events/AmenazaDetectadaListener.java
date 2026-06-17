package ar.edu.utn.ba.ddsi.smartlife.security_service.events;

import ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto.AmenazaDetectadaEvent;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.services.EventoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AmenazaDetectadaListener {

    private static final Logger log = LoggerFactory.getLogger(AmenazaDetectadaListener.class);

    private final EventoService eventoService;

    public AmenazaDetectadaListener(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    @RabbitListener(queues = "${smartlife.events.queues.amenaza-detectada}")
    public void onAmenazaDetectada(AmenazaDetectadaEvent event) {
        if (event == null || event.hogarId() == null || event.tipo() == null) {
            log.warn("AmenazaDetectada recibida sin hogarId/tipo, se ignora");
            return;
        }
        log.info("Procesando AmenazaDetectada hogarId={} tipo={}", event.hogarId(), event.tipo());
        try {
            eventoService.registrarAmenaza(event);
        } catch (ResourceNotFoundException ex) {
            log.warn("Hogar no encontrado en security-service, se ignora la amenaza. hogarId={}",
                event.hogarId());
        }
    }
}
