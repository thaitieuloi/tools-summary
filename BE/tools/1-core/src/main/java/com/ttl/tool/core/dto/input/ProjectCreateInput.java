package com.ttl.tool.core.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input DTO for creating a new project
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreateInput {

    private String name;
    private String description;
    private String status; // ACTIVE, INACTIVE, COMPLETED, ARCHIVED
    private Boolean active;
}
