package ar.edu.utn.ba.ddsi.smartlife.trends_service.config;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ComercioRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

	private final ComercioRepository comercioRepository;

	public DataInitializer(ComercioRepository comercioRepository) {
		this.comercioRepository = comercioRepository;
	}

	@PostConstruct
	void cargarDatosIniciales() {
		Comercio comercio = new Comercio();
		comercio.setNombre("Comercio Demo");
		comercioRepository.save(comercio);
	}
}
