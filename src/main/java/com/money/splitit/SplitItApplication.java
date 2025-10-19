package com.money.splitit;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SplitItApplication implements CommandLineRunner {

    @Value("${jwt.secret}")
    private String jwtSecret;

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SplitItApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
	}

    @Override
    public void run(String... args) {
        System.out.println("JWT Secret: " + jwtSecret);
    }
}