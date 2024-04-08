package com.paulomarchon.reactivedorotechtest.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "PauloMarchon",
                        email = "example@exemple.com",
                        url = "https://example.com/teste"
                ),
                description = "OpenApi documentation for REST service",
                title = "OpenApi - Dorotech Java Test",
                version = "1.0",
                license = @License(
                        name = "MIT",
                        url = "https://exemple.com"
                ),
                termsOfService = "Terms and Services"
        ),
        servers = {
                @Server(
                        description = "Development environment ",
                        url = "https://exemple.com"
                ),
                @Server(
                        description = "Production environment",
                        url = "https://exemple.com"
                )
        }
)
public class OpenApiConfig {
}
