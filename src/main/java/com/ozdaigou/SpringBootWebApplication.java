package com.ozdaigou;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootWebApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootWebApplication.class);

    public static void main(String[] args) {

        final ApplicationContext ctx = SpringApplication.run(SpringBootWebApplication.class, args);

        logger.warn("Let's inspect the beans provided by Spring Boot:");

        final String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.warn(beanName);
        }
    }
}
