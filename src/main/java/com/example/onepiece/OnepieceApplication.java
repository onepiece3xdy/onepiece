package com.example.onepiece;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.onepiece.Entity")
@EnableJpaRepositories(basePackages = "com.example.onepiece.Repository")

public class OnepieceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnepieceApplication.class, args);
	}

}
