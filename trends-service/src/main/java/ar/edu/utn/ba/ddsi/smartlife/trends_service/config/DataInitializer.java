package ar.edu.utn.ba.ddsi.smartlife.trends_service.config;

import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.producto.Producto;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.EnAuge;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.models.entities.tendencia.Normal;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.trends_service.repositories.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	private final ComercioRepository comercioRepository;
    private final ProductoRepository productoRepository;

	public DataInitializer(ComercioRepository comercioRepository, ProductoRepository productoRepository) {
		this.comercioRepository = comercioRepository;
        this.productoRepository = productoRepository;
	}

    @Bean
    CommandLineRunner cargarDatosIniciales() {
        return args -> {
            Comercio comercio = new Comercio();
            comercio.setNombre("ElectroMart");
            comercioRepository.save(comercio);

            Producto smartTv50 =  Producto.builder()
                    .nombre("Smart Tv 50")
                    .categoria("Electrónicos")
                    .precioBase(250000)
                    .comercio(comercio)
                    .estado(new Normal())
                    .build();
            productoRepository.save(smartTv50);

            Normal.setVentasMinimasParaAscender(3);
            EnAuge.setLikesMinimasParaAscender(2);
            EnAuge.setVentasMinimasParaAscender(5);
            EnAuge.setLikesMinimosParaDescender(4);
        };
	}
}
