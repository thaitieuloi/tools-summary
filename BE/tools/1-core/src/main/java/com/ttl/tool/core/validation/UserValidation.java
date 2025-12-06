package com.ttl.tool.core.validation;

import am.ik.yavi.builder.ValidatorBuilder;
import com.ttl.tool.core.dto.input.UserCreateInput;
import com.ttl.tool.core.dto.input.UserUpdateInput;
import com.ttl.tool.domain.repository.UserRepository;

/**
 * Centralized validation rules for User entity.
 * Used by Commands to validate inputs.
 */
public class UserValidation {

        /**
         * Validation rules for creating a user
         */
        public static ValidatorBuilder<UserCreateInput> create(UserRepository userRepository) {
                return ValidatorBuilder.<UserCreateInput>of()
                                .constraint(UserCreateInput::getUsername, "username", c -> c
                                                .notBlank().message("Username is required")
                                                .lessThanOrEqual(50).message("Username must not exceed 50 characters")
                                                .pattern("[a-zA-Z0-9_]+")
                                                .message("Username can only contain letters, numbers and underscore"))
                                .constraint(UserCreateInput::getEmail, "email", c -> c
                                                .notBlank().message("Email is required")
                                                .email().message("Email must be a valid email address"))
                                // Check uniqueness via Repository
                                .constraintOnTarget(input -> !userRepository.existsByUsername(input.getUsername()),
                                                "username",
                                                "error.username.exists",
                                                "Username already exists");
        }

        /**
         * Validation rules for updating a user
         * Fields are optional, validation only applies if field is not null.
         */
        public static ValidatorBuilder<UserUpdateInput> update() {
                return ValidatorBuilder.<UserUpdateInput>of()
                                .constraintOnTarget(input -> input.getId() != null, "id", "id.required",
                                                "ID is required")
                                .constraint(UserUpdateInput::getUsername, "username", c -> c
                                                .lessThanOrEqual(50).message("Username must not exceed 50 characters")
                                                .pattern("[a-zA-Z0-9_]+")
                                                .message("Username can only contain letters, numbers and underscore"))
                                .constraint(UserUpdateInput::getEmail, "email", c -> c
                                                .email().message("Invalid email format"));
        }
}
