package com.ttl.common.graphql;

import com.ttl.common.core.CoreCommonConfig;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Common GraphQL Configuration
 * This configuration is shared across all microservices
 * Provides common GraphQL scalars
 * SPQR auto-configuration is enabled automatically via spring-boot-starter
 */
@Configuration
@ComponentScan(basePackages = "com.ttl.common.graphql")
@Import(CoreCommonConfig.class)
public class GraphQLCommonConfig {

    /**
     * Register UUID scalar type for GraphQL
     */
    @Bean
    public GraphQLScalarType uuidScalar() {
        return ExtendedScalars.UUID;
    }

    /**
     * Register DateTime scalar type for GraphQL
     */
    @Bean
    public GraphQLScalarType dateTimeScalar() {
        return ExtendedScalars.DateTime;
    }

    /**
     * Register Date scalar type for GraphQL
     */
    @Bean
    public GraphQLScalarType dateScalar() {
        return ExtendedScalars.Date;
    }
}
