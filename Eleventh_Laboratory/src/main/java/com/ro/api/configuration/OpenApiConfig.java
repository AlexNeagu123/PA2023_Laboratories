package com.ro.api.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "GomokuApi", version = "0.0.1-SNAPSHOT"),
        servers = {@Server(url = "http://localhost:8081")},
        tags = {
                @Tag(name = "Player", description = "There are players related requests."),
                @Tag(name = "Game", description = "There are games related requests.")
        })
public class OpenApiConfig {
}
