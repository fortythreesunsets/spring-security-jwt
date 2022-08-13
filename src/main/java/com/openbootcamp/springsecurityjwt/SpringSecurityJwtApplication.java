package com.openbootcamp.springsecurityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Clase punto de entrada app Spring Boot
 */
@SpringBootApplication
//@EnableWebMvc
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {

		// Contenedor de beans
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}

}
