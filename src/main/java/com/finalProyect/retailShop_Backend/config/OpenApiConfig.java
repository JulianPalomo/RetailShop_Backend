package com.finalProyect.retailShop_Backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Retail Shop API")
                        .version("1.0")
                        .description("Retail Shop API implemented with Spring Boot RESTful service"));
    }
}
