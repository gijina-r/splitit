package com.money.splitit;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication

public class SplitItApplication {

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SplitItApplication.class);
        app.setAdditionalProfiles("dev");
        app.run(args);
	}
}