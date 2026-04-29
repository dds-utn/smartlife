package ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.inmemory;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.utils.GeneradorIdSecuencial;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryComercioRepository implements ComercioRepository {

	private final List<Comercio> comercios = new ArrayList<>();
	private final GeneradorIdSecuencial generadorId = new GeneradorIdSecuencial();

	@Override
	public List<Comercio> findAll() {
		return new ArrayList<>(comercios);
	}

	@Override
	public Optional<Comercio> findById(Long id) {
		return comercios.stream().filter(c -> c.getId().equals(id)).findFirst();
	}

	@Override
	public Comercio save(Comercio comercio) {
		if (comercio.getId() == null) {
			comercio.setId(generadorId.siguiente());
			comercios.add(comercio);
			return comercio;
		}
		delete(comercio);
		comercios.add(comercio);
		return comercio;
	}

	@Override
	public void delete(Comercio comercio) {
		if (comercio.getId() == null) {
			return;
		}
		comercios.removeIf(c -> c.getId().equals(comercio.getId()));
	}
}
