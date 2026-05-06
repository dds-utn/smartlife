package ar.edu.utn.ba.ddsi.smartlife.trends_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrendsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendsServiceApplication.class, args);
	}
}
