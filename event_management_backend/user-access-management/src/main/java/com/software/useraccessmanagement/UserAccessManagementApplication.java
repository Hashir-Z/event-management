package com.software.useraccessmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication(
        scanBasePackages = {
                "com.software.useraccessmanagement",
        }
)
@EnableFeignClients(
        basePackages = "com.software.clients"
)
@PropertySources({
        @PropertySource("classpath:clients-${spring.profiles.active}.properties")
})
public class UserAccessManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserAccessManagementApplication.class, args);
    }
}