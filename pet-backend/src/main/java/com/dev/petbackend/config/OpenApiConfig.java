package com.dev.petbackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Pet Care API",
                version = "1.0",
                description = "Documentation for endpoints in Pet Cate"
        )
)
public class OpenApiConfig {
        // http://localhost:9192/api/v1/swagger-ui/index.html
}
