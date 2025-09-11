package com.eventos.senac.apieventos_senac.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi() {

        return new OpenAPI().info(new Info().title("API Eventos Senac")
                                            .version("1.0")
                                            .description("Api Gerenciamento de  Eventos")
                                            .termsOfService("http://")

        );
    }

    //Para acessar a pagina do swagger
    //http://localhost:8080/swagger-ui/index.html#/usuario-controller

}
