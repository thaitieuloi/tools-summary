package com.ttl.common.core.command;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * Context for command execution
 * Can hold validation errors and other execution metadata
 */
@Data
public class Context {

    private Map<String, Object> attributes = new HashMap<>();
    private Object constraintViolations;
    private String userId;
    private String tenantId;

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        return (T) attributes.get(key);
    }
}
