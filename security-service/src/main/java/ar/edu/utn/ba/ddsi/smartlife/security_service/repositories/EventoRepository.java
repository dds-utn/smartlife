package ar.edu.utn.ba.ddsi.smartlife.security_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Evento;

import java.util.List;
import java.util.Optional;

public interface EventoRepository {
    Optional<Evento> findById(Long id);
    List<Evento> findByHogarId(Long hogarId);
    Evento save(Evento evento);
}
