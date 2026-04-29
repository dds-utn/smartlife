package ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;

import java.util.List;
import java.util.Optional;

public interface ComercioRepository {

	List<Comercio> findAll();

	Optional<Comercio> findById(Long id);

	Comercio save(Comercio comercio);

	void delete(Comercio comercio);
}
