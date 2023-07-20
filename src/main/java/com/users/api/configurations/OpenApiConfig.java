package com.users.api.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI createOpenApi(){
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Url del server para realizar las prueba de la solución");

        Contact contact = new Contact();
        contact.setEmail("ceauro@gmail.com");
        contact.setName("César Rodríguez");

        Info info = new Info()
                .contact(contact)
                .version("1.0")
                .description("API que expone los endpoints para la prueba teécnica de ingreso a Nisum.")
                .title("User API");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
