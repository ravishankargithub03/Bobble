package com.babble.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "MS REST User API Documentation",
				description = """
						MS 1.O Developement in Spring 3.1.0 With Microservices,
						Modules Are - Company Module
						""",
				version = "MS 1.O"
		),
		externalDocs = @ExternalDocumentation(
				description = "My Self",
				url = "https://www.myself.net/"
		)
)

@SpringBootApplication
public class BabbleApplication {

	public static void main(String[] args) {
		SpringApplication.run(BabbleApplication.class, args);
	}

}
