package com.nudgeLearn.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI nudgeLearnOpenApi() {
		return new OpenAPI()
				.info(new Info()
						.title("NudgeLearn API")
						.version("0.0.1")
						.description("MVP de micro-learning: temas, quizzes y respuestas."));
	}
}
