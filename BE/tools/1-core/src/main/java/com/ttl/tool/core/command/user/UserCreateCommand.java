package com.ttl.tool.core.command.user;

import am.ik.yavi.builder.ValidatorBuilder;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.crud.BaseCrudCreateCommandV2;
import com.ttl.common.core.mapper.EntityMapper;
import com.ttl.tool.core.dto.input.UserCreateInput;
import com.ttl.tool.core.validation.UserValidation;
import com.ttl.tool.domain.entity.User;
import com.ttl.tool.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Command for creating a new User.
 * 
 * Features:
 * - Auto-mapping from UserCreateInput to User entity
 * - Input validation using YAVI
 * - Business logic hooks (beforeCreate, beforeSave, afterSave)
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * UserCreateInput input = UserCreateInput.builder()
 *         .username("john_doe")
 *         .email("john@example.com")
 *         .build();
 * 
 * UUID userId = userCreateCommand.execute(input);
 * }
 * </pre>
 */
@Service
@Slf4j
public class UserCreateCommand extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {

    private final UserRepository userRepository;

    public UserCreateCommand(UserRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
        this.userRepository = repository;
    }

    /**
     * Define validation rules using YAVI
     */
    @Override
    protected ValidatorBuilder<UserCreateInput> getValidatorBuilder(CommandHolder<UserCreateInput> holder) {
        return UserValidation.create(userRepository);
    }

    // Example: Set additional fields before save
    @Override
    protected User beforeSave(User entity, UserCreateInput input, CommandHolder<UserCreateInput> holder) {
        // Set default values if not provided in input
        if (input.getActive() == null) {
            entity.setActive(true);
        }
        return entity;
    }
}
