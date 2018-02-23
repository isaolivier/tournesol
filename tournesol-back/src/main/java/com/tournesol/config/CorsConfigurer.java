package com.tournesol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by iolivier on 07/07/2017.
 */
@Configuration
public class CorsConfigurer {

    @Bean
    public WebMvcConfigurer CorsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:8080",
                        "http://tournesol.samsonmultiservices.fr:8080",
                        "http://tournesol.samsonmultiservices.fr");
            }
        };
    }
}
