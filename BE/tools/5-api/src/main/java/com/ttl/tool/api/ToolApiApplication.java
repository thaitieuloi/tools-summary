package com.ttl.tool.api;

import com.ttl.common.graphql.GraphQLCommonConfig;
import com.ttl.tool.boot.ToolBaseConfigBoot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ttl.tool.api",
        "com.ttl.tool.core"
})
@Import({ GraphQLCommonConfig.class, ToolBaseConfigBoot.class })
public class ToolApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToolApiApplication.class, args);
    }

}
