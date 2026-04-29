package ar.edu.utn.ba.ddsi.smartlife.trends_service.config;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.EnAuge;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.Normal;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ComercioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	private final ComercioRepository comercioRepository;

	public DataInitializer(ComercioRepository comercioRepository) {
		this.comercioRepository = comercioRepository;
	}

    @Bean
    CommandLineRunner cargarDatosIniciales() {
        return args -> {
            Comercio comercio = new Comercio();
            comercio.setNombre("Comercio Demo");
            comercioRepository.save(comercio);

            Normal.setVentasMinimasParaAscender(3);
            EnAuge.setLikesMinimasParaAscender(2);
            EnAuge.setVentasMinimasParaAscender(5);
            EnAuge.setLikesMinimosParaDescender(4);
        };
	}
}
