package com.buildtogether.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI buildTogetherOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BuildTogether API")
                        .description("A comprehensive hackathon management system with team collaboration features. " +
                                "This API provides endpoints for managing users, skills, hackathons, teams, and project submissions.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("BuildTogether Team")
                                .email("support@buildtogether.com")
                                .url("https://buildtogether.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server"),
                        new Server()
                                .url("https://api.buildtogether.com")
                                .description("Production Server")
                ));
    }
}
