package com.ttl.tool.core.validation;

import am.ik.yavi.builder.ValidatorBuilder;
import com.ttl.tool.core.dto.input.ProjectCreateInput;
import com.ttl.tool.core.dto.input.ProjectUpdateInput;
import com.ttl.tool.domain.repository.ProjectRepository;

/**
 * Centralized validation rules for Project entity.
 * Used by Commands to validate inputs.
 */
public class ProjectValidation {

    /**
     * Validation rules for creating a project
     */
    public static ValidatorBuilder<ProjectCreateInput> create(ProjectRepository projectRepository) {
        return ValidatorBuilder.<ProjectCreateInput>of()
                .constraint(ProjectCreateInput::getName, "name", c -> c
                        .notBlank().message("Project name is required")
                        .lessThanOrEqual(255).message("Project name must not exceed 255 characters"))
                .constraint(ProjectCreateInput::getDescription, "description", c -> c
                        .lessThanOrEqual(1000).message("Description must not exceed 1000 characters"))
                .constraint(ProjectCreateInput::getStatus, "status", c -> c
                        .pattern("ACTIVE|INACTIVE|COMPLETED|ARCHIVED")
                        .message("Status must be one of: ACTIVE, INACTIVE, COMPLETED, ARCHIVED"))
                // Check uniqueness via Repository
                .constraintOnTarget(input -> !projectRepository.existsByName(input.getName()),
                        "name",
                        "error.project.name.exists",
                        "Project name already exists");
    }

    /**
     * Validation rules for updating a project
     * Fields are optional, validation only applies if field is not null.
     */
    public static ValidatorBuilder<ProjectUpdateInput> update() {
        return ValidatorBuilder.<ProjectUpdateInput>of()
                .constraintOnTarget(input -> input.getId() != null, "id", "id.required",
                        "ID is required")
                .constraint(ProjectUpdateInput::getName, "name", c -> c
                        .lessThanOrEqual(255).message("Project name must not exceed 255 characters"))
                .constraint(ProjectUpdateInput::getDescription, "description", c -> c
                        .lessThanOrEqual(1000).message("Description must not exceed 1000 characters"))
                .constraint(ProjectUpdateInput::getStatus, "status", c -> c
                        .pattern("ACTIVE|INACTIVE|COMPLETED|ARCHIVED")
                        .message("Status must be one of: ACTIVE, INACTIVE, COMPLETED, ARCHIVED"));
    }
}
