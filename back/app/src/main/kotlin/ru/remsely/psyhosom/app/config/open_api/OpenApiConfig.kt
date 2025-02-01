package ru.remsely.psyhosom.app.config.open_api

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class OpenApiConfig {
    @Bean
    fun customOpenApi(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Psychosom Backend API")
                    .version("1")
                    .description("API серверной части приложения Psychosom")
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
}
