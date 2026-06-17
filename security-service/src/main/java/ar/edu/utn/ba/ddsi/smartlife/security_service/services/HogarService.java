package ar.edu.utn.ba.ddsi.smartlife.security_service.services;

import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.evento.EventoResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar.HogarResponse;
import ar.edu.utn.ba.ddsi.smartlife.security_service.dtos.hogar.SeguridadUpdateRequest;

import java.util.List;

public interface HogarService {

    /**
     * Devuelve la información del hogar sólo si está «seguro» (§6.5).
     */
    HogarResponse findById(Long id);

    List<EventoResponse> findEventos(Long id);

    HogarResponse actualizarSeguridad(Long id, SeguridadUpdateRequest request);
}
