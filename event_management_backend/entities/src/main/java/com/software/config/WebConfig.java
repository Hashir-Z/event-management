package com.software.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configure allowed origins, methods, and headers
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")  // React frontend or other frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
        System.out.println("Were are in addCorsMappings\n\n\n\n\n\n\n\n");
    }
}