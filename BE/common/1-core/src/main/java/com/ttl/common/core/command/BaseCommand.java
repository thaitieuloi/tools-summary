package com.ttl.common.core.command;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

/**
 * Base abstract class for all commands.
 * Provides template method pattern for command execution with:
 * - Lifecycle hooks (onBefore, onExecute, onSuccess, onError)
 * - YAVI validation support
 * - Error handling
 * 
 * @param <In>  Input type
 * @param <Out> Output type
 */
@Data
@Slf4j
public abstract class BaseCommand<In, Out> implements Command<In, Out> {

    public BaseCommand() {
        init();
    }

    /**
     * Initialization hook - called in constructor
     */
    protected void init() {
        // Override if needed
    }

    /**
     * Main execution logic - must be implemented by subclasses
     */
    protected abstract Out onExecute(CommandHolder<In> holder);

    /**
     * Get validator builder for input validation.
     * Override this method to define validation rules using YAVI.
     * 
     * Example:
     * 
     * <pre>
     * {@code
     * @Override
     * protected ValidatorBuilder<UserCreateInput> getValidatorBuilder(CommandHolder<UserCreateInput> holder) {
     *     return ValidatorBuilder.<UserCreateInput>of()
     *             .constraint(UserCreateInput::getUsername, "username", c -> c.notBlank().lessThanOrEqual(50))
     *             .constraint(UserCreateInput::getEmail, "email", c -> c.notBlank().email());
     * }
     * }
     * </pre>
     */
    protected ValidatorBuilder<In> getValidatorBuilder(CommandHolder<In> holder) {
        return null;
    }

    /**
     * Get validator from builder.
     * Can be overridden if you want to provide validator directly.
     */
    protected Validator<In> getValidator(CommandHolder<In> holder) {
        ValidatorBuilder<In> builder = getValidatorBuilder(holder);
        return builder == null ? null : builder.build();
    }

    /**
     * Validation hook - validates input using YAVI validator
     */
    protected void validate(CommandHolder<In> holder) {
        In input = holder.getInput();

        // Skip validation if input is null
        if (input == null) {
            return;
        }

        Validator<In> validator = getValidator(holder);
        if (validator != null) {
            ConstraintViolations violations = validator.validate(input);

            if (!violations.isValid()) {
                // Store violations in context for later access
                holder.getContext().setAttribute("violations", violations);

                // Build error message
                String errorMessage = violations.stream()
                        .map(v -> v.name() + ": " + v.message())
                        .collect(Collectors.joining(", "));

                log.warn("Validation failed for {}: {}", this.getClass().getSimpleName(), errorMessage);
                throw new ValidationException("Validation failed: " + errorMessage, violations);
            }
        }
    }

    /**
     * Before execution hook
     */
    protected void onBefore(CommandHolder<In> holder) {
        // Override to implement pre-execution logic
        log.debug("Executing command: {}", this.getClass().getSimpleName());
    }

    /**
     * Success hook - called after successful execution
     */
    protected Out onSuccess(CommandHolder<In> holder, Out output) {
        // Override to implement post-execution logic
        log.debug("Command executed successfully: {}", this.getClass().getSimpleName());
        return output;
    }

    /**
     * Error hook - called when exception occurs
     */
    protected RuntimeException onError(CommandHolder<In> holder, Exception ex) {
        log.error("Command execution failed: {}", this.getClass().getSimpleName(), ex);
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }
        return new RuntimeException("Command execution failed", ex);
    }

    /**
     * Template method for command execution
     */
    @Override
    public Out execute(CommandHolder<In> holder) {
        try {
            validate(holder);
            onBefore(holder);
            Out output = onExecute(holder);
            return onSuccess(holder, output);
        } catch (Exception ex) {
            throw onError(holder, ex);
        }
    }
}
