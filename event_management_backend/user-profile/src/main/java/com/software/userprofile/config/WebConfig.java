package com.software.userprofile.config;  // Use the correct package structure

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all endpoints to be accessed by the frontend
        registry.addMapping("/**")  // This applies to all endpoints
                .allowedOrigins("http://localhost:3000")  // Adjust to match the frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow required methods
                .allowedHeaders("*");  // Allow all headers
    }
}
