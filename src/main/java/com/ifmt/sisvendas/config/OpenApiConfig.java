package com.ifmt.sisvendas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sgcvpOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SGCVP API")
                        .description("API REST do Sistema de Gestão de Compra e Venda de Produtos.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipe SGCVP")
                                .email("contato@ifmt.edu.br"))
                        .license(new License()
                                .name("Uso acadêmico")));
    }
}
