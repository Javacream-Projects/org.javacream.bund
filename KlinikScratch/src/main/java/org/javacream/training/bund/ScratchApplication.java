package org.javacream.training.bund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories()
public class ScratchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScratchApplication.class, args);
	}
}
