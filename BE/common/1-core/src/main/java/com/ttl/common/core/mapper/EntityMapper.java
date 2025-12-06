package com.ttl.common.core.mapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Simple entity mapper utility for copying properties between objects.
 * Supports field-to-field mapping with optional field name transformations.
 */
@Component
@Slf4j
public class EntityMapper {

    /**
     * Map properties from source to target object.
     * Only maps fields with matching names and compatible types.
     * 
     * @param source Source object
     * @param target Target object
     * @param <S>    Source type
     * @param <T>    Target type
     * @return The target object with mapped fields
     */
    public <S, T> T map(S source, T target) {
        return map(source, target, new HashSet<>());
    }

    /**
     * Map properties from source to target object, excluding specified fields.
     * 
     * @param source        Source object
     * @param target        Target object
     * @param excludeFields Fields to exclude from mapping
     * @param <S>           Source type
     * @param <T>           Target type
     * @return The target object with mapped fields
     */
    public <S, T> T map(S source, T target, Set<String> excludeFields) {
        if (source == null || target == null) {
            return target;
        }

        Map<String, Field> sourceFields = getAllFields(source.getClass());
        Map<String, Field> targetFields = getAllFields(target.getClass());

        for (Map.Entry<String, Field> entry : sourceFields.entrySet()) {
            String fieldName = entry.getKey();

            // Skip excluded fields
            if (excludeFields.contains(fieldName)) {
                continue;
            }

            Field sourceField = entry.getValue();
            Field targetField = targetFields.get(fieldName);

            if (targetField != null && isCompatible(sourceField, targetField)) {
                copyField(source, target, sourceField, targetField);
            }
        }

        return target;
    }

    /**
     * Create a new instance of target class and map properties from source.
     * 
     * @param source      Source object
     * @param targetClass Target class
     * @param <S>         Source type
     * @param <T>         Target type
     * @return New instance of target class with mapped fields
     */
    public <S, T> T map(S source, Class<T> targetClass) {
        try {
            T target = targetClass.getDeclaredConstructor().newInstance();
            return map(source, target);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + targetClass.getName(), e);
        }
    }

    /**
     * Get all fields from a class including inherited fields
     */
    private Map<String, Field> getAllFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        Class<?> current = clazz;

        while (current != null && current != Object.class) {
            for (Field field : current.getDeclaredFields()) {
                fields.putIfAbsent(field.getName(), field);
            }
            current = current.getSuperclass();
        }

        return fields;
    }

    /**
     * Check if two fields are compatible for mapping
     */
    private boolean isCompatible(Field sourceField, Field targetField) {
        // Same type is always compatible
        if (sourceField.getType().equals(targetField.getType())) {
            return true;
        }

        // Check if target type is assignable from source type
        return targetField.getType().isAssignableFrom(sourceField.getType());
    }

    /**
     * Copy field value from source to target
     */
    private <S, T> void copyField(S source, T target, Field sourceField, Field targetField) {
        try {
            sourceField.setAccessible(true);
            targetField.setAccessible(true);

            Object value = sourceField.get(source);

            // Skip null values
            if (value != null) {
                targetField.set(target, value);
            }
        } catch (Exception e) {
            log.warn("Failed to copy field: {} from {} to {}",
                    sourceField.getName(),
                    source.getClass().getSimpleName(),
                    target.getClass().getSimpleName(),
                    e);
        }
    }

    /**
     * Update target object with non-null values from source
     * 
     * @param source Source object
     * @param target Target object
     * @param <T>    Object type
     * @return Updated target object
     */
    public <T> T update(T source, T target) {
        return map(source, target);
    }

    /**
     * Update specific fields on target from source
     * 
     * @param source     Source object
     * @param target     Target object
     * @param fieldNames Fields to update
     * @param <T>        Object type
     * @return Updated target object
     */
    public <T> T updateFields(T source, T target, String... fieldNames) {
        if (source == null || target == null) {
            return target;
        }

        Map<String, Field> fields = getAllFields(source.getClass());

        for (String fieldName : fieldNames) {
            Field field = fields.get(fieldName);
            if (field != null) {
                copyField(source, target, field, field);
            }
        }

        return target;
    }
}
