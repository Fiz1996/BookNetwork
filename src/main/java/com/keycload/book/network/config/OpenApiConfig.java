package com.keycload.book.network.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Alibou",
                        email = "contact",
                        url = "https://testing"
                ),
                description = "OpenApi documentation for Spring security ",
                title = "OpenApi specification " ,
                version = "1.0.0",
                license =  @License(
                        name = "Licence name",
                        url = "https://www.google.com"
                ),
                termsOfService = "Terms of service"

        ),
        servers = {
                @Server(
                        description = "Local ENV ",
                        url = "http://localhost:8080"
                )
        },

        security = {
                        @SecurityRequirement(
                                name = "bearerAuth"
                        )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER

)
public class OpenApiConfig {
}
