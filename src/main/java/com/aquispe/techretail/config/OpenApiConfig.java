package com.aquispe.techretail.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TechRetail Clientes API")
                        .version("1.0.0")
                        .description("API REST para la gestión de clientes, cálculo de KPIs y estimación de expectativa de vida.")
                        .contact(new Contact()
                                .name("TechRetail Support")
                                .email("support@techretail.com")));
    }
}
