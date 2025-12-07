package com.ttl.tool.core.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Input DTO for updating an existing project
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateInput {

    private UUID id;
    private String name;
    private String description;
    private String status; // ACTIVE, INACTIVE, COMPLETED, ARCHIVED
    private Boolean active;
}
