package ar.edu.utn.ba.ddsi.smartlife.security_service.config;

import ar.edu.utn.ba.ddsi.smartlife.security_service.models.entities.Hogar;
import ar.edu.utn.ba.ddsi.smartlife.security_service.repositories.HogarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    public static final long HOGAR_DEFAULT_ID = 1L;

    @Bean
    public CommandLineRunner seedData(HogarRepository hogarRepository) {
        return args -> {
            // El hogar 1 coincide con el id por defecto del security-service-in-home.
            if (hogarRepository.findById(HOGAR_DEFAULT_ID).isEmpty()) {
                hogarRepository.save(new Hogar(HOGAR_DEFAULT_ID, "Av. Siempre Viva 742", "+54 11 4000-0000"));
            }
            if (hogarRepository.findById(2L).isEmpty()) {
                hogarRepository.save(new Hogar(2L, "Calle Falsa 123", "+54 11 4111-1111"));
            }
        };
    }
}
