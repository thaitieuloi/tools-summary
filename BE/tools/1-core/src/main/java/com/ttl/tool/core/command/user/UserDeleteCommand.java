package com.ttl.tool.core.command.user;

import com.ttl.common.core.command.crud.BaseCrudDeleteCommand;
import com.ttl.tool.domain.entity.User;
import com.ttl.tool.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Command for deleting a User.
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * UUID userId = UUID.fromString("...");
 * userDeleteCommand.execute(userId);
 * }
 * </pre>
 */
@Service
@Slf4j
public class UserDeleteCommand extends BaseCrudDeleteCommand<User, UUID> {

    public UserDeleteCommand(UserRepository repository) {
        super(repository);
    }

    // That's it! Deletion is handled automatically.

    // If you need custom logic, override the hooks:

    // Example: Validation before deletion
    // @Override
    // protected void beforeDelete(UUID id, User entity, CommandHolder<UUID> holder)
    // {
    // if (entity.getUsername().equals("admin")) {
    // throw new IllegalStateException("Cannot delete admin user");
    // }
    // }

    // Example: Soft delete instead of hard delete
    // @Override
    // protected void performDelete(UUID id, User entity) {
    // entity.setActive(false);
    // repository.save(entity);
    // }

    // Example: Cleanup after deletion
    // @Override
    // protected void afterDelete(UUID id, User entity, CommandHolder<UUID> holder)
    // {
    // log.info("User deleted successfully: {}", id);
    // // eventPublisher.publish(new UserDeletedEvent(id));
    // // cacheManager.evict("users", id);
    // }
}
