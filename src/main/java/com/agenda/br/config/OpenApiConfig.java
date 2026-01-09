package com.agenda.br.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Agenda API",
        version = "1.0.0",
        description = "API REST para gerenciamento de contatos",
        contact = @Contact(
            name = "Suporte",
            email = "suporte@email.com",
            url = "https://www.seusite.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8080",
            description = "Servidor de Desenvolvimento"
        )
    }
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customizeOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
                .title("Agenda API")
                .version("1.0.0")
                .description("Documentação da API de Agenda")
                .termsOfService("https://www.seusite.com/terms")
                .contact(new io.swagger.v3.oas.models.info.Contact()
                    .name("Equipe de Suporte")
                    .email("suporte@email.com"))
                .license(new io.swagger.v3.oas.models.info.License()
                    .name("Apache 2.0")
                    .url("http://springdoc.org")));
    }
}