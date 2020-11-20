package com.bakery.shark.bakery_shark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BakerySharkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakerySharkApplication.class, args);
	}

}
