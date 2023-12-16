package com.moneymong.global.config.docs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"local", "dev", "prod"})
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title("Moneymong API Document");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}