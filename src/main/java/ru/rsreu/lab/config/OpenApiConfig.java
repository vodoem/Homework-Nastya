package ru.rsreu.lab.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("JWT", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

    /*@Bean
    public OpenAPI labOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Audio Text Formatter API")
                        .description("API для распознавания аудио и форматирования текста")
                        .version("v1.0"))
                .components(new Components()
                        .addSecuritySchemes("JWT", createAPIKeyScheme()));  // 👈 Имя "JWT"
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Введите JWT токен в формате: Bearer {token}");
    }*/
}