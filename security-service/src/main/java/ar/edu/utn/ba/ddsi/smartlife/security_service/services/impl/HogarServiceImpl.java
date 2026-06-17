package ar.edu.utn.ba.ddsi.smartlife.security_service.services.impl;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento.EventoResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar.HogarResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar.SeguridadUpdateRequest;
import ar.edu.utn.ba.ddsi.smartlife.security_service.events.HogarEventPublisher;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.BusinessException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.HogarEnPeligroException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.exceptions.ResourceNotFoundException;
import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.EstadoHogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.EventoRepository;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.HogarRepository;
import ar.edu.utn.ba.ddsi.smartlife.security_service.services.HogarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HogarServiceImpl implements HogarService {

    private final HogarRepository hogarRepository;
    private final EventoRepository eventoRepository;
    private final HogarEventPublisher hogarEventPublisher;

    public HogarServiceImpl(HogarRepository hogarRepository,
                            EventoRepository eventoRepository,
                            HogarEventPublisher hogarEventPublisher) {
        this.hogarRepository = hogarRepository;
        this.eventoRepository = eventoRepository;
        this.hogarEventPublisher = hogarEventPublisher;
    }

    @Override
    public HogarResponse findById(Long id) {
        Hogar hogar = buscarHogar(id);
        if (!hogar.estaSeguro()) {
            throw new HogarEnPeligroException(
                "El hogar " + id + " está en peligro; su información no está disponible");
        }
        return HogarResponse.from(hogar);
    }

    @Override
    public List<EventoResponse> findEventos(Long id) {
        buscarHogar(id);
        return eventoRepository.findByHogarId(id).stream()
            .map(EventoResponse::from)
            .toList();
    }

    @Override
    public HogarResponse actualizarSeguridad(Long id, SeguridadUpdateRequest request) {
        Hogar hogar = buscarHogar(id);
        EstadoHogar nuevoEstado = parsearEstado(request);
        if (nuevoEstado != EstadoHogar.SEGURO) {
            throw new BusinessException(
                "El estado del hogar sólo puede cambiarse manualmente a SEGURO");
        }
        hogar.marcarSeguro();
        hogarRepository.save(hogar);
        hogarEventPublisher.publicarHogarSeguro(hogar.getId());
        return HogarResponse.from(hogar);
    }

    private Hogar buscarHogar(Long id) {
        return hogarRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró hogar con id " + id));
    }

    private EstadoHogar parsearEstado(SeguridadUpdateRequest request) {
        if (request == null || request.estado() == null || request.estado().isBlank()) {
            throw new BusinessException("El estado es obligatorio");
        }
        try {
            return EstadoHogar.valueOf(request.estado().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Estado inválido: " + request.estado());
        }
    }
}
