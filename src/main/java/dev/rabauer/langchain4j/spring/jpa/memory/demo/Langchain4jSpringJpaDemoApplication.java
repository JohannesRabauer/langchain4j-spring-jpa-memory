package dev.rabauer.langchain4j.spring.jpa.memory.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Spring Boot application.
 */
@SpringBootApplication
public class Langchain4jSpringJpaDemoApplication {

    /**
     * Boots the Spring application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Langchain4jSpringJpaDemoApplication.class, args);
    }
}
