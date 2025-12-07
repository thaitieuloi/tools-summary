package com.ttl.tool.notification.api;

import com.ttl.common.graphql.GraphQLCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ttl.tool.notification.api",
        "com.ttl.tool.notification.core"
})
@Import(GraphQLCommonConfig.class)
public class NotificationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApiApplication.class, args);
    }

}
