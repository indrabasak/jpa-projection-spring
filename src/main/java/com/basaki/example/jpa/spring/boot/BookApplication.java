package com.basaki.example.jpa.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * {@code BookApplication} represents the entry point for book controller
 * spring boot application.
 * <p/>
 *
 * @author Indra Basak
 * @since 9/22/17
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.basaki.example.jpa.spring.config",
        "com.basaki.example.jpa.spring.controller",
        "com.basaki.example.jpa.spring.data.model",
        "com.basaki.example.jpa.spring.data.repository",
        "com.basaki.example.jpa.spring.error",
        "com.basaki.example.jpa.spring.service"})
@EntityScan(basePackages = "com.basaki.example.jpa.spring.data.model")
@EnableJpaRepositories(basePackages = {"com.basaki.example.jpa.spring.data.repository"})
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
