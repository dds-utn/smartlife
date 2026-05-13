package ar.edu.utn.ba.ddsi.smartlife.sales_service.config;

import ar.edu.utn.frba.ddsi.common.smartlife.logging.Logger;
import ar.edu.utn.frba.ddsi.common.smartlife.logging.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public Logger logger() {
        return LoggerFactory.createConsoleLogger("sales-service");
    }
}
