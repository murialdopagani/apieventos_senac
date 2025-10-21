package com.eventos.senac.apieventos_senac.infra.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class EncodingConfig {


    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilterRegistrationBean() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistrationBean<CharacterEncodingFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setOrder(Integer.MIN_VALUE); // executar antes de outros filtros
        registration.setName("customCharacterEncodingFilter");
        return registration;
    }
}
