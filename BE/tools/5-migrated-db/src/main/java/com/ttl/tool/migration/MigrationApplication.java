package com.ttl.tool.migration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Standalone application for running database migrations using Liquibase.
 * This application should be run separately before starting the main API
 * application.
 * 
 * Usage:
 * - Development: ./gradlew :migrated-db:migrate
 * - Production: java -jar tool-migration.jar
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.ttl.tool.migration")
public class MigrationApplication {

    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("Starting Database Migration...");
        System.out.println("=".repeat(60));

        SpringApplication app = new SpringApplication(MigrationApplication.class);
        // Exit after migration completes
        System.exit(SpringApplication.exit(app.run(args)));
    }
}
