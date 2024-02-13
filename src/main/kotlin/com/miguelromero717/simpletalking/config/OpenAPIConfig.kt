package com.miguelromero717.simpletalking.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {
    @Bean
    fun myOpenAPI(): OpenAPI {
        val contact = Contact()
        contact.email = "miguelromero717@gmail.com"
        contact.name = "Miguel Romero"
        contact.url = "https://github.com/miguelromero717"

        val info =
            Info()
                .title("Simple Talking API")
                .description("API to send and receive messages from the chat")
                .version("v0.0.1")

        return OpenAPI().info(info.contact(contact))
    }
}
