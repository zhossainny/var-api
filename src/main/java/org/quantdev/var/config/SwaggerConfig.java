package org.quantdev.var.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Swagger/OpenAPI configuration for the VaR Calculator API.
 * Sets up API metadata for Swagger UI and API documentation.
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VaR Calculator API")
                        .version("1.0")
                        .description("REST API for calculating historical Value at Risk (VaR)")
                );
    }
}
