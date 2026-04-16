package ar.edu.utn.ba.ddsi.smartlife.sales_service.config;

import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.comercio.Comercio;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.impuestos.EI;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.impuestos.EO;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.impuestos.IVA;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.models.entities.productos.TipoProducto;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.ComercioRepository;
import ar.edu.utn.ba.ddsi.smartlife.sales_service.repositories.TipoProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

	public static final long TIPO_HOGAR_ID = 1L;
	public static final long TIPO_ELECTRONICO_ID = 2L;
	public static final long COMERCIO_DEFAULT_ID = 1L;

	@Bean
	public CommandLineRunner seedData(TipoProductoRepository tipoProductoRepository, ComercioRepository comercioRepository) {
		return args -> {
			if (tipoProductoRepository.findById(TIPO_HOGAR_ID).isEmpty()) {
				TipoProducto hogar = new TipoProducto(TIPO_HOGAR_ID, "Hogar");
				hogar.agregarImpuestos(new IVA(), new EI());
				tipoProductoRepository.save(hogar);
			}
			if (tipoProductoRepository.findById(TIPO_ELECTRONICO_ID).isEmpty()) {
				TipoProducto electronico = new TipoProducto(TIPO_ELECTRONICO_ID, "Electrónico");
				electronico.agregarImpuestos(new IVA(), new EO());
				tipoProductoRepository.save(electronico);
			}
			if (comercioRepository.findById(COMERCIO_DEFAULT_ID).isEmpty()) {
				comercioRepository.save(new Comercio(COMERCIO_DEFAULT_ID));
			}
		};
	}
}
