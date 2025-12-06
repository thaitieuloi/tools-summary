package com.ttl.common.core.command.crud;

import com.ttl.common.core.command.BaseCommand;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.TransactionalCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Generic base command for UPDATE operations.
 * Automatically handles entity updates with minimal boilerplate.
 * 
 * Usage:
 * 
 * <pre>
 * {@code
 * @Service
 * public class UserUpdateCommand
 *         extends BaseCrudUpdateCommand<User, UserUpdateInput, UUID> {
 * 
 *     public UserUpdateCommand(UserRepository repository) {
 *         super(repository);
 *     }
 * 
 *     // Override updateEntity() for custom update logic.
 * }
 * }
 * </pre>
 * 
 * @param <Entity> JPA Entity type
 * @param <Input>  Input DTO type (should contain ID and fields to update)
 * @param <ID>     Entity ID type
 */
@Slf4j
public abstract class BaseCrudUpdateCommand<Entity, Input, ID>
        extends BaseCommand<Input, Entity>
        implements TransactionalCommand<Input, Entity> {

    protected final JpaRepository<Entity, ID> repository;
    private final Class<Entity> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseCrudUpdateCommand(JpaRepository<Entity, ID> repository) {
        this.repository = repository;
        this.entityClass = (Class<Entity>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Main execution logic - updates the entity
     */
    @Override
    protected Entity onExecute(CommandHolder<Input> holder) {
        Input input = holder.getInput();

        // Extract ID from input
        ID id = extractId(input);

        // Ensure ID is non-null for null-safety compliance
        Objects.requireNonNull(id, "Entity ID cannot be null");

        // Find existing entity
        Entity existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Entity not found with ID: %s", id)));

        // Allow subclasses to modify input before update
        input = beforeUpdate(input, existingEntity, holder);

        // Update entity fields
        existingEntity = Objects.requireNonNull(
                updateEntity(existingEntity, input, holder),
                "updateEntity() returned null");

        // Allow subclasses to modify entity before save
        existingEntity = Objects.requireNonNull(
                beforeSave(existingEntity, input, holder),
                "beforeSave() returned null");

        // Save the updated entity
        Entity savedEntity = Objects.requireNonNull(
                repository.save(existingEntity),
                "Repository save operation returned null");

        // Allow subclasses to perform post-save operations
        afterSave(savedEntity, input, holder);

        return savedEntity;
    }

    /**
     * Extract ID from input DTO
     * Must be implemented by subclasses
     */
    protected abstract ID extractId(Input input);

    /**
     * Hook: called before entity update
     * Override to modify input before applying changes
     */
    protected Input beforeUpdate(Input input, Entity existingEntity, CommandHolder<Input> holder) {
        return input;
    }

    /**
     * Update entity fields from input.
     * Must be implemented by subclasses for specific update logic.
     */
    protected abstract @NonNull Entity updateEntity(@NonNull Entity entity, Input input, CommandHolder<Input> holder);

    /**
     * Hook: called before saving updated entity
     * Override to modify entity before persistence
     */
    protected @NonNull Entity beforeSave(@NonNull Entity entity, Input input, CommandHolder<Input> holder) {
        return entity;
    }

    /**
     * Hook: called after entity is updated
     * Override to perform additional operations (e.g., send events, invalidate
     * cache)
     */
    protected void afterSave(@NonNull Entity savedEntity, Input input, CommandHolder<Input> holder) {
        // Override if needed
    }

    protected Class<Entity> getEntityClass() {
        return entityClass;
    }
}
