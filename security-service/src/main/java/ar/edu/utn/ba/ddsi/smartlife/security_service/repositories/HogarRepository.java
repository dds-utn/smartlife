package ar.edu.utn.ba.ddsi.smartlife.security_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Hogar;

import java.util.List;
import java.util.Optional;

public interface HogarRepository {
    List<Hogar> findAll();
    Optional<Hogar> findById(Long id);
    Hogar save(Hogar hogar);
}
