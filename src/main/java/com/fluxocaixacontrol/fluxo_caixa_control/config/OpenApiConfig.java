package com.fluxocaixacontrol.fluxo_caixa_control.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Sistema de Fluxo de Caixa")
                .description("Controle de lançamentos com saldo consolidado")
                .version("1.0.0"));
    }
}