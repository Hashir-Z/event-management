package com.software.useraccessmanagement.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WebConfigTest {

    @Test
    public void testRestTemplateBeanConfiguration() {
        ApplicationContext context = new AnnotationConfigApplicationContext(WebConfig.class);

        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        assertNotNull(restTemplate, "The RestTemplate bean should not be null");
    }
}
