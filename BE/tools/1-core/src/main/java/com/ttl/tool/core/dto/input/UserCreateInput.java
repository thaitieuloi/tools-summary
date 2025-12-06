package com.ttl.tool.core.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input DTO for creating a new user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateInput {

    private String username;
    private String email;

    // Additional fields if needed
    private Boolean active;
}
