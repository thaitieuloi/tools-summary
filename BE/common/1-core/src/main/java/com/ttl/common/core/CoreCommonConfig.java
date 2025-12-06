package com.ttl.common.core;

import com.ttl.common.base.JpaCommonConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Core Common Configuration
 * This configuration provides core features for microservices
 */
@Configuration
@ComponentScan(basePackages = "com.ttl.common.core")
@Import(JpaCommonConfig.class)
public class CoreCommonConfig {
    // Core configuration imports JPA configuration
}
