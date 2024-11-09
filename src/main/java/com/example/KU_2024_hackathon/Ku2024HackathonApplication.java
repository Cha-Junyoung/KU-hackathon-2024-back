package com.example.KU_2024_hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Ku2024HackathonApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ku2024HackathonApplication.class, args);
	}

}
