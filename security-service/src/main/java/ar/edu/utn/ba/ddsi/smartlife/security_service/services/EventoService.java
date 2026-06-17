package ar.edu.utn.ba.ddsi.smartlife.security_service.services;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento.EventoResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.events.dto.AmenazaDetectadaEvent;

public interface EventoService {

    EventoResponse findById(Long id);

    /**
     * Registra una amenaza detectada (recibida del broker), poniendo el hogar
     * «en peligro» y publicando el evento HogarEnPeligro.
     */
    EventoResponse registrarAmenaza(AmenazaDetectadaEvent event);
}
