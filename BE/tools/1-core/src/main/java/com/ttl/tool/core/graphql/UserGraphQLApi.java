package com.ttl.tool.core.graphql;

import com.ttl.tool.core.command.user.UserCreateCommand;
import com.ttl.tool.core.command.user.UserDeleteCommand;
import com.ttl.tool.core.command.user.UserUpdateCommand;
import com.ttl.tool.core.dto.input.UserCreateInput;
import com.ttl.tool.core.dto.input.UserUpdateInput;
import com.ttl.tool.domain.entity.User;
import com.ttl.tool.domain.repository.UserRepository;
import com.ttl.tool.shared.dto.UserSearchInput;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL API for User operations.
 * Uses Command Pattern to execute business logic.
 * 
 * Architecture:
 * GraphQL API → Command → Repository
 * 
 * Example usage:
 * - Queries (getUser, searchUsers) call Repository directly
 * - Mutations (userCreate, userUpdate, userDelete) call Commands
 */
@Component
@GraphQLApi
@RequiredArgsConstructor
public class UserGraphQLApi {

    // Commands for mutations
    private final UserCreateCommand userCreateCommand;
    private final UserUpdateCommand userUpdateCommand;
    private final UserDeleteCommand userDeleteCommand;

    // Repository for queries (queries don't need commands)
    private final UserRepository userRepository;

    /**
     * Query: Get a single user by ID
     */
    @GraphQLQuery(description = "Get user by ID")
    public User userFindById(@GraphQLArgument(name = "id") String id) {
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    /**
     * Query: Search users with pagination
     */
    @GraphQLQuery(description = "Search users with filters")
    public List<User> userSearch(
            @GraphQLArgument(name = "filter") UserSearchInput filter,
            @GraphQLArgument(name = "page") Integer page,
            @GraphQLArgument(name = "size") Integer size) {

        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<User> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }

    /**
     * Mutation: Create a new user
     * Uses UserCreateCommand to execute the business logic
     * 
     * @return The ID of the created user
     */
    @GraphQLMutation(name = "userCreate", description = "Create a new user")
    public UUID userCreate(@GraphQLArgument(name = "input") UserCreateInput input) {
        // Execute the command and return the created user ID
        return userCreateCommand.execute(input);
    }

    /**
     * Mutation: Update an existing user
     * Uses UserUpdateCommand to execute the business logic
     * 
     * @return The updated user entity
     */
    @GraphQLMutation(description = "Update an existing user")
    public User userUpdate(
            @GraphQLArgument(name = "id") String id,
            @GraphQLArgument(name = "input") UserUpdateInput input) {

        // Set the ID in the input
        input.setId(UUID.fromString(id));

        // Execute the command and return the updated user entity
        return userUpdateCommand.execute(input);
    }

    /**
     * Mutation: Delete a user
     * Uses UserDeleteCommand to execute the business logic
     * 
     * @return true if deletion was successful
     */
    @GraphQLMutation(description = "Delete a user")
    public Boolean userDelete(@GraphQLArgument(name = "id") String id) {
        // Execute the command and return success status
        userDeleteCommand.execute(UUID.fromString(id));
        return true;
    }
}
