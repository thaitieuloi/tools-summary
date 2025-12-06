package com.ttl.common.core.command;

import am.ik.yavi.core.ConstraintViolations;
import lombok.Getter;

/**
 * Exception thrown when command input validation fails.
 * Contains the YAVI ConstraintViolations for detailed error information.
 */
@Getter
public class ValidationException extends RuntimeException {

    private final ConstraintViolations violations;

    public ValidationException(String message, ConstraintViolations violations) {
        super(message);
        this.violations = violations;
    }

    /**
     * Get formatted error message with all violations
     */
    public String getDetailedMessage() {
        if (violations == null || violations.isValid()) {
            return getMessage();
        }

        StringBuilder sb = new StringBuilder(getMessage());
        sb.append("\nViolations:");
        violations.forEach(v -> {
            sb.append("\n  - ").append(v.name()).append(": ").append(v.message());
        });
        return sb.toString();
    }
}
