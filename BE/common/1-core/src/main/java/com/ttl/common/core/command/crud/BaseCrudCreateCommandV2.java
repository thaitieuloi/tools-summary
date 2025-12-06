package com.ttl.common.core.command.crud;

import com.ttl.common.core.command.BaseCommand;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.TransactionalCommand;
import com.ttl.common.core.mapper.EntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.lang.reflect.ParameterizedType;

/**
 * Improved generic base command for CREATE operations with automatic mapping.
 * Uses EntityMapper for automatic field mapping from Input to Entity.
 * 
 * Usage Example 1 - Simple auto-mapping:
 * 
 * <pre>
 * {@code
 * @Service
 * public class UserCreateCommand
 *         extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
 * 
 *     public UserCreateCommand(UserRepository repository, EntityMapper mapper) {
 *         super(repository, mapper);
 *     }
 * 
 *     // That's it! Auto-mapping handles everything.
 * }
 * }
 * </pre>
 * 
 * Usage Example 2 - With custom mapping:
 * 
 * <pre>
 * {
 *     &#64;code
 *     &#64;Service
 *     public class UserCreateCommand
 *             extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
 * 
 *         public UserCreateCommand(UserRepository repository, EntityMapper mapper) {
 *             super(repository, mapper);
 *         }
 * 
 *         @Override
 *         protected User mapInputToEntity(UserCreateInput input, CommandHolder<UserCreateInput> holder) {
 *             User user = super.mapInputToEntity(input, holder);
 *             // Add custom logic
 *             user.setPassword(passwordEncoder.encode(input.getPassword()));
 *             return user;
 *         }
 *     }
 * }
 * </pre>
 * 
 * @param <Entity> JPA Entity type
 * @param <Input>  Input DTO type
 * @param <ID>     Entity ID type
 */
@Slf4j
public abstract class BaseCrudCreateCommandV2<Entity, Input, ID>
        extends BaseCommand<Input, ID>
        implements TransactionalCommand<Input, ID> {

    protected final JpaRepository<Entity, ID> repository;
    protected final EntityMapper entityMapper;
    private final Class<Entity> entityClass;

    @SuppressWarnings("unchecked")
    protected BaseCrudCreateCommandV2(JpaRepository<Entity, ID> repository, EntityMapper entityMapper) {
        this.repository = repository;
        this.entityMapper = entityMapper;
        this.entityClass = (Class<Entity>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    protected ID onExecute(CommandHolder<Input> holder) {
        Input input = holder.getInput();

        // Hook: before creation
        input = beforeCreate(input, holder);

        // Map input to entity (with auto-mapping)
        Entity entity = mapInputToEntity(input, holder);

        // Hook: before save
        entity = beforeSave(entity, input, holder);

        // Save entity
        Entity savedEntity = repository.save(entity);

        // Hook: after save
        afterSave(savedEntity, input, holder);

        // Extract and return ID
        return extractId(savedEntity);
    }

    /**
     * Map input DTO to entity using EntityMapper.
     * Override this for custom mapping logic while still using auto-mapping as
     * base.
     */
    protected @NonNull Entity mapInputToEntity(Input input, CommandHolder<Input> holder) {
        try {
            // Create new entity instance
            Entity entity = entityClass.getDeclaredConstructor().newInstance();

            // Auto-map fields from input to entity
            entityMapper.map(input, entity, getExcludedFields());

            // Ensure non-null for null-safety compliance
            return java.util.Objects.requireNonNull(entity, "Entity instantiation returned null");
        } catch (Exception e) {
            throw new RuntimeException("Failed to create and map entity. " +
                    "Check that entity has a no-args constructor.", e);
        }
    }

    /**
     * Override to specify fields that should NOT be auto-mapped
     */
    protected java.util.Set<String> getExcludedFields() {
        // Typically exclude: id, createdAt, updatedAt, etc.
        return java.util.Set.of("id", "createdAt", "updatedAt", "createdBy", "updatedBy");
    }

    /**
     * Hook: called before entity creation
     */
    protected Input beforeCreate(Input input, CommandHolder<Input> holder) {
        return input;
    }

    /**
     * Hook: called before saving entity
     */
    protected @NonNull Entity beforeSave(@NonNull Entity entity, Input input, CommandHolder<Input> holder) {
        return entity;
    }

    /**
     * Hook: called after entity is saved
     */
    protected void afterSave(Entity savedEntity, Input input, CommandHolder<Input> holder) {
        // Override if needed
    }

    /**
     * Extract ID from saved entity
     */
    @SuppressWarnings("unchecked")
    protected ID extractId(Entity entity) {
        try {
            var idField = entity.getClass().getDeclaredField("id");
            idField.setAccessible(true);
            return (ID) idField.get(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract ID. Override extractId() if needed.", e);
        }
    }

    protected Class<Entity> getEntityClass() {
        return entityClass;
    }
}
