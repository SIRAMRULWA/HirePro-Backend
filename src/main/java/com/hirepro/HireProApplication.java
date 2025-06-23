package com.hirepro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.hirepro.repository")
@EntityScan(basePackages = "com.hirepro.model")
public class HireProApplication {
	public static void main(String[] args) {
		SpringApplication.run(HireProApplication.class, args);
	}
}