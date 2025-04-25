package com.software;

import com.software.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfig.class)
public class Entity {
    public static void main(String[] args) {
        SpringApplication.run(Entity.class, args);
    }
}
