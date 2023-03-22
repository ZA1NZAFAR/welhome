package fr.efrei.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = {"fr.efrei.backend.*"})
//@EnableJpaRepositories(basePackages = {"fr.efrei.backend.repositories.*"})
//@EntityScan(basePackages = {"fr.efrei.backend.entities.*"})
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
