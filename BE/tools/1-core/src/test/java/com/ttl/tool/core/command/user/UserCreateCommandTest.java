package com.ttl.tool.core.command.user;

import com.ttl.common.core.mapper.EntityMapper;
import com.ttl.tool.core.dto.input.UserCreateInput;
import com.ttl.tool.domain.entity.User;
import com.ttl.tool.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit test example for UserCreateCommand
 */
@ExtendWith(MockitoExtension.class)
class UserCreateCommandTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper entityMapper;

    private UserCreateCommand command;

    @BeforeEach
    void setUp() {
        command = new UserCreateCommand(userRepository, entityMapper);
    }

    @Test
    void execute_shouldCreateUserSuccessfully() {
        // Given
        UserCreateInput input = UserCreateInput.builder()
                .username("john_doe")
                .email("john@example.com")
                .active(true)
                .build();

        UUID expectedId = UUID.randomUUID();
        User savedUser = User.builder()
                .id(expectedId)
                .username("john_doe")
                .email("john@example.com")
                .active(true)
                .build();

        // Mock EntityMapper to populate the entity
        doAnswer(invocation -> {
            User entity = invocation.getArgument(1);
            entity.setUsername(input.getUsername());
            entity.setEmail(input.getEmail());
            entity.setActive(input.getActive());
            return entity;
        }).when(entityMapper).map(eq(input), any(User.class), any());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UUID result = command.execute(input);

        // Then
        assertThat(result).isEqualTo(expectedId);

        // Verify repository.save was called
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo("john_doe");
        assertThat(capturedUser.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void execute_shouldUseEntityMapper() {
        // Given
        UserCreateInput input = UserCreateInput.builder()
                .username("jane_doe")
                .email("jane@example.com")
                .build();

        User savedUser = User.builder()
                .id(UUID.randomUUID())
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        command.execute(input);

        // Then
        verify(entityMapper).map(eq(input), any(User.class), any());
    }
}
