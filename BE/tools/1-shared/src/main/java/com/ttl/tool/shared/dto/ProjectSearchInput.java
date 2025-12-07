package com.ttl.tool.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Input DTO for searching projects
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSearchInput {

    private String name;
    private String status;
    private Boolean active;
}
