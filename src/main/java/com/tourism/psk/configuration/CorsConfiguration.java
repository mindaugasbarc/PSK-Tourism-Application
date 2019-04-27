package com.tourism.psk.configuration;


import com.tourism.psk.constants.Globals;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "PUT", "GET", "DELETE", "PATCH")
                        .allowedHeaders(Globals.ACCESS_TOKEN_HEADER_NAME, "Content-Type")
                        .exposedHeaders(Globals.ACCESS_TOKEN_HEADER_NAME)
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}
