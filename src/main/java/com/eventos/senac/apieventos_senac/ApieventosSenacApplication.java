package com.eventos.senac.apieventos_senac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApieventosSenacApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(ApieventosSenacApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ApieventosSenacApplication.class);
    }
}
