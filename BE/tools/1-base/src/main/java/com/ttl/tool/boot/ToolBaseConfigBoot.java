package com.ttl.tool.boot;

import com.ttl.common.base.JpaCommonConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Base Configuration Boot
 * This is the base configuration that can be extended by other configurations
 */
@Configuration
@Import(JpaCommonConfig.class)
public class ToolBaseConfigBoot {

}
