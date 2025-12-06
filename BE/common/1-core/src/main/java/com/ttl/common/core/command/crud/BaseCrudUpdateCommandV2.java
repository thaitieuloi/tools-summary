package com.ttl.common.core.command.crud;

import com.ttl.common.core.command.BaseCommand;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.TransactionalCommand;
import com.ttl.common.core.mapper.EntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.ParameterizedType;

/**
 * Improved generic base command for UPDATE operations with automatic mapping.
 * Uses EntityMapper for automatic field updates.
 * 
 * Usage Example:
 * 
 * <pre>
 * {
 *     &#64;code
 *     &#64;Service
 *     public class UserUpdateCommand
 *             extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {
 * 
 *         public UserUpdateCommand(UserRepository repository, EntityMapper mapper) {
 *             super(repository, mapper);
 *         }
 * 
 *         @Override
 *         protected UUID extractId(UserUpdateInput input) {
 *             return input.getId();
 *         }
 * 
 *         // Auto-mapping handles the rest!
 *     }
 * }
 * </pre>
 * 
 * @param <Entity> JPA Entity type
 * @param <Input>  Input DTO type
 * @param <ID>     Entity ID type
 */
@Slf4j
public abstract class BaseCrudUpdateCommandV2<Entity, Input, ID>
        extends BaseCommand<Input, Entity>
        implements TransactionalCommand<Input, Entity> {

    protected final JpaRepository<Entity, ID> repository;
    protected final EntityMapper entityMapper;
    private final Class<Entity> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseCrudUpdateCommandV2(JpaRepository<Entity, ID> repository, EntityMapper entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
        this.entityClass = (Class<Entity>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    protected Entity onExecute(CommandHolder<Input> holder) {
        Input input = holder.getInput();

        // Extract ID
        ID id = extractId(input);
        java.util.Objects.requireNonNull(id, "ID must not be null");

        // Find existing entity
        Entity existingEntity = repository.findById(id)
                .orElseThrow(() -> newEntityNotFoundException(id));

        // Hook: before update
        input = beforeUpdate(input, existingEntity, holder);

        // Update entity (with auto-mapping)
        existingEntity = updateEntity(existingEntity, input, holder);

        // Hook: before save
        existingEntity = beforeSave(existingEntity, input, holder);

        // Save updated entity
        @SuppressWarnings("null")
        Entity savedEntity = repository.save(existingEntity);

        // Hook: after save
        afterSave(savedEntity, input, holder);

        return savedEntity;
    }

    /**
     * Extract ID from input DTO - MUST be implemented
     */
    protected abstract ID extractId(Input input);

    /**
     * Update entity with values from input using auto-mapping.
     * Override for custom update logic.
     */
    protected Entity updateEntity(Entity entity, Input input, CommandHolder<Input> holder) {
        // Auto-map non-excluded fields from input to entity
        entityMapper.map(input, entity, getExcludedFields());
        return entity;
    }

    /**
     * Override to specify fields that should NOT be auto-updated
     */
    protected java.util.Set<String> getExcludedFields() {
        // Typically exclude: id, createdAt, createdBy
        return java.util.Set.of("id", "createdAt", "createdBy");
    }

    /**
     * Hook: called before update
     */
    protected Input beforeUpdate(Input input, Entity existingEntity, CommandHolder<Input> holder) {
        return input;
    }

    /**
     * Hook: called before save
     */
    protected Entity beforeSave(Entity entity, Input input, CommandHolder<Input> holder) {
        return entity;
    }

    /**
     * Hook: called after entity is updated
     */
    protected void afterSave(Entity savedEntity, Input input, CommandHolder<Input> holder) {
        // Override if needed
    }

    /**
     * Create exception for entity not found
     */
    protected RuntimeException newEntityNotFoundException(ID id) {
        return new RuntimeException(
                String.format("%s not found with ID: %s",
                        entityClass.getSimpleName(), id));
    }

    protected Class<Entity> getEntityClass() {
        return entityClass;
    }
}
