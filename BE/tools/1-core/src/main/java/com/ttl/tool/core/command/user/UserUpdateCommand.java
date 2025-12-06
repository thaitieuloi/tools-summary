package com.ttl.tool.core.command.user;

import am.ik.yavi.builder.ValidatorBuilder;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.crud.BaseCrudUpdateCommandV2;
import com.ttl.common.core.mapper.EntityMapper;
import com.ttl.tool.core.dto.input.UserUpdateInput;
import com.ttl.tool.core.validation.UserValidation;
import com.ttl.tool.domain.entity.User;
import com.ttl.tool.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Command for updating an existing User.
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * UserUpdateInput input = UserUpdateInput.builder()
 *         .id(userId)
 *         .username("john_doe_updated")
 *         .email("john.new@example.com")
 *         .build();
 * 
 * User updatedUser = userUpdateCommand.execute(input);
 * }
 * </pre>
 */
@Service
@Slf4j
public class UserUpdateCommand extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {

    public UserUpdateCommand(UserRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
    }

    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();
    }

    /**
     * Define validation rules using YAVI
     * Note: For update, fields are optional. Only validate if present.
     */
    @Override
    protected ValidatorBuilder<UserUpdateInput> getValidatorBuilder(CommandHolder<UserUpdateInput> holder) {
        return UserValidation.update();
    }
}
