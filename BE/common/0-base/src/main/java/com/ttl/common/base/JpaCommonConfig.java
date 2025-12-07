package com.ttl.common.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Common JPA configuration for all projects
 * - Enables JPA Auditing for automatic createdAt/updatedAt fields
 * - Enables JPA repositories scanning
 */
@Configuration
@EnableJpaRepositories(basePackages = { "com.ttl" })
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = { "com.ttl" })
@EnableJpaAuditing
public class JpaCommonConfig {
    // JPA configuration is automatically handled by Spring Boot
}
