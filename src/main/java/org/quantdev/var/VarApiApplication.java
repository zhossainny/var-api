package org.quantdev.var;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Main entry point for the VaR Calculator Spring Boot application.
 * Starts the REST API server.
 */
@SpringBootApplication
public class VarApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(VarApiApplication.class, args);
    }
}