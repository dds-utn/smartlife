package ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ComercioRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryComercioRepository implements ComercioRepository {

	private final List<Comercio> comercios = new ArrayList<>();

	@Override
	public List<Comercio> findAll() {
		return new ArrayList<>(comercios);
	}

	@Override
	public Optional<Comercio> findById(Long id) {
		return comercios.stream().filter(c -> c.getId() == id).findFirst();
	}

	@Override
	public Comercio save(Comercio comercio) {
		delete(comercio);
		comercios.add(comercio);
		return comercio;
	}

	@Override
	public void delete(Comercio comercio) {
		comercios.removeIf(c -> c.getId() == comercio.getId());
	}
}
