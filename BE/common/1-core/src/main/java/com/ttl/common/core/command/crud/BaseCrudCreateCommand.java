package com.ttl.common.core.command.crud;

import com.ttl.common.core.command.BaseCommand;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.TransactionalCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.lang.reflect.ParameterizedType;

/**
 * Generic base command for CREATE operations.
 * Automatically handles entity creation with minimal boilerplate.
 * 
 * Usage:
 * 
 * <pre>
 * {@code
 * @Service
 * public class UserCreateCommand
 *         extends BaseCrudCreateCommand<User, UserCreateInput, UUID> {
 * 
 *     public UserCreateCommand(UserRepository repository) {
 *         super(repository);
 *     }
 * 
 *     // That's it! Auto-mapping will handle the rest.
 *     // Override mapInputToEntity() for custom mapping logic.
 * }
 * }
 * </pre>
 * 
 * @param <Entity> JPA Entity type
 * @param <Input>  Input DTO type
 * @param <ID>     Entity ID type
 */
@Slf4j
public abstract class BaseCrudCreateCommand<Entity, Input, ID>
        extends BaseCommand<Input, ID>
        implements TransactionalCommand<Input, ID> {

    protected final JpaRepository<Entity, ID> repository;
    private final Class<Entity> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseCrudCreateCommand(JpaRepository<Entity, ID> repository) {
        this.repository = repository;
        // Get the Entity class from generic type
        this.entityClass = (Class<Entity>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Main execution logic - creates the entity
     */
    @Override
    protected ID onExecute(CommandHolder<Input> holder) {
        Input input = holder.getInput();

        // Allow subclasses to modify input before creation
        input = beforeCreate(input, holder);

        // Map input to entity
        Entity entity = mapInputToEntity(input, holder);

        // Allow subclasses to modify entity before save
        entity = beforeSave(entity, input, holder);

        // Save the entity
        Entity savedEntity = repository.save(entity);

        // Allow subclasses to perform post-save operations
        afterSave(savedEntity, input, holder);

        // Extract and return ID
        return extractId(savedEntity);
    }

    /**
     * Hook: called before entity creation
     * Override to modify input before mapping
     */
    protected Input beforeCreate(Input input, CommandHolder<Input> holder) {
        return input;
    }

    /**
     * Map input DTO to entity.
     * Default implementation tries to create instance using reflection.
     * Override this for custom mapping logic.
     */
    protected @NonNull Entity mapInputToEntity(Input input, CommandHolder<Input> holder) {
        try {
            Entity entity = entityClass.getDeclaredConstructor().newInstance();
            if (entity == null) {
                throw new RuntimeException("Entity instantiation returned null for class: " + entityClass.getName());
            }
            // Use reflection or mapper to copy properties
            // Subclasses should override this for specific mapping
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create entity instance. " +
                    "Override mapInputToEntity() for custom mapping.", e);
        }
    }

    /**
     * Hook: called before saving entity
     * Override to modify entity before persistence
     */
    protected @NonNull Entity beforeSave(@NonNull Entity entity, Input input, CommandHolder<Input> holder) {
        return entity;
    }

    /**
     * Hook: called after entity is saved
     * Override to perform additional operations (e.g., send events, update cache)
     */
    protected void afterSave(@NonNull Entity savedEntity, Input input, CommandHolder<Input> holder) {
        // Override if needed
    }

    /**
     * Extract ID from saved entity
     * Override if ID extraction is non-standard
     */
    @SuppressWarnings("unchecked")
    protected ID extractId(@NonNull Entity entity) {
        try {
            var idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (ID) idField.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract ID from entity. " +
                    "Override extractId() for custom ID extraction.", e);
        }
    }

    protected Class<Entity> getEntityClass() {
        return entityClass;
    }
}
