package ar.edu.utn.ba.ddsi.smartlife.security_service.services.impl;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento.EventoResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.events.HogarEventPublisher;
import ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto.AmenazaDetectadaEvent;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Evento;
import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.GradoRiesgo;
import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.TipoDeEvento;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.EventoRepository;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.HogarRepository;
import ar.edu.utn.ba.ddsi.smartlife.security_service.services.EventoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventoServiceImpl implements EventoService {

    private static final Logger log = LoggerFactory.getLogger(EventoServiceImpl.class);

    private final EventoRepository eventoRepository;
    private final HogarRepository hogarRepository;
    private final HogarEventPublisher hogarEventPublisher;

    public EventoServiceImpl(EventoRepository eventoRepository,
                             HogarRepository hogarRepository,
                             HogarEventPublisher hogarEventPublisher) {
        this.eventoRepository = eventoRepository;
        this.hogarRepository = hogarRepository;
        this.hogarEventPublisher = hogarEventPublisher;
    }

    @Override
    public EventoResponse findById(Long id) {
        Evento evento = eventoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró evento con id " + id));
        return EventoResponse.from(evento);
    }

    @Override
    public EventoResponse registrarAmenaza(AmenazaDetectadaEvent event) {
        if (event == null || event.hogarId() == null || event.tipo() == null) {
            throw new BusinessException("La amenaza detectada debe incluir hogarId y tipo");
        }
        Hogar hogar = hogarRepository.findById(event.hogarId())
            .orElseThrow(() -> new ResourceNotFoundException(
                "No se encontró hogar con id " + event.hogarId()));

        Evento evento = new Evento(
            event.hogarId(),
            TipoDeEvento.valueOf(event.tipo()),
            event.fechaHora() != null ? event.fechaHora() : LocalDateTime.now(),
            event.gradoRiesgo() != null ? GradoRiesgo.valueOf(event.gradoRiesgo()) : null,
            event.accionesEjecutadas()
        );
        eventoRepository.save(evento);

        hogar.agregarEvento(evento.getId());
        hogar.marcarEnPeligro();
        hogarRepository.save(hogar);

        hogarEventPublisher.publicarHogarEnPeligro(hogar.getId(), evento.getTipo().name());
        log.info("Amenaza registrada: eventoId={} hogarId={} tipo={}",
            evento.getId(), hogar.getId(), evento.getTipo());

        return EventoResponse.from(evento);
    }
}
