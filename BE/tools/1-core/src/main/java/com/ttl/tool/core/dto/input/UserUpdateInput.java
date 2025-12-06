package com.ttl.tool.core.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Input DTO for updating an existing user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInput {

    private UUID id;
    private String username;
    private String email;
    private Boolean active;
}
