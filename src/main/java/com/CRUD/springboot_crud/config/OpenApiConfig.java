package com.CRUD.springboot_crud.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("My Spring Boot CRUD API")
                        .description("My Spring Boot CRUD API with swagger ")
                        .version("V.1.0"));
    }
}
