package com.ltc.telegrambotlinkedin.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Product Management"),
                description = "OpenApi documentation for Frontend",
                title = "CareerCompanion Bot",
                version = "1.0",
                termsOfService = "Terms of service"
        )
)
public class SwaggerConfig {}
