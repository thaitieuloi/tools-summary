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
 * Generic base command for DELETE operations.
 * Automatically handles entity deletion with minimal boilerplate.
 * 
 * Usage:
 * 
 * <pre>
 * {@code
 * @Service
 * public class UserDeleteCommand
 *         extends BaseCrudDeleteCommand<User, UUID> {
 * 
 *     public UserDeleteCommand(UserRepository repository) {
 *         super(repository);
 *     }
 * 
 *     // That's it! Deletion is handled automatically.
 *     // Override beforeDelete() or afterDelete() for custom logic.
 * }
 * }
 * </pre>
 * 
 * @param <Entity> JPA Entity type
 * @param <ID>     Entity ID type
 */
@Slf4j
public abstract class BaseCrudDeleteCommand<Entity, ID>
        extends BaseCommand<ID, Void>
        implements TransactionalCommand<ID, Void> {

    protected final JpaRepository<Entity, ID> repository;
    private final Class<Entity> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseCrudDeleteCommand(JpaRepository<Entity, ID> repository) {
        this.repository = repository;
        this.entityClass = (Class<Entity>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Main execution logic - deletes the entity
     */
    @Override
    protected Void onExecute(CommandHolder<ID> holder) {
        ID id = Objects.requireNonNull(holder.getInput(), "ID cannot be null for delete operation");

        // Find existing entity (optional - for validation or soft delete)
        Entity existingEntity = findEntity(id);

        // Allow subclasses to perform pre-delete operations
        beforeDelete(id, existingEntity, holder);

        // Perform the deletion
        performDelete(id, existingEntity);

        // Allow subclasses to perform post-delete operations
        afterDelete(id, existingEntity, holder);

        return null;
    }

    /**
     * Find entity by ID.
     * Override to customize entity retrieval or to skip retrieval for hard deletes.
     */
    protected Entity findEntity(@NonNull ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Entity not found with ID: %s", id)));
    }

    /**
     * Hook: called before entity deletion
     * Override to perform validation or business logic checks
     */
    protected void beforeDelete(@NonNull ID id, Entity entity, CommandHolder<ID> holder) {
        // Override if needed
    }

    /**
     * Perform the actual deletion.
     * Override for soft delete implementation.
     */
    protected void performDelete(@NonNull ID id, Entity entity) {
        repository.deleteById(id);
    }

    /**
     * Hook: called after entity is deleted
     * Override to perform additional operations (e.g., send events, cleanup)
     */
    protected void afterDelete(@NonNull ID id, Entity entity, CommandHolder<ID> holder) {
        // Override if needed
    }

    protected Class<Entity> getEntityClass() {
        return entityClass;
    }
}
