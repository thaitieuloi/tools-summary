package com.ttl.tool.core.command.project;

import am.ik.yavi.builder.ValidatorBuilder;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.crud.BaseCrudCreateCommandV2;
import com.ttl.common.core.mapper.EntityMapper;
import com.ttl.tool.core.dto.input.ProjectCreateInput;
import com.ttl.tool.core.validation.ProjectValidation;
import com.ttl.tool.domain.entity.Project;
import com.ttl.tool.domain.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Command for creating a new Project.
 * 
 * Features:
 * - Auto-mapping from ProjectCreateInput to Project entity
 * - Input validation using YAVI
 * - Business logic hooks (beforeCreate, beforeSave, afterSave)
 * - URN field is automatically generated via @PostPersist callback in entity
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * ProjectCreateInput input = ProjectCreateInput.builder()
 *         .name("My Project")
 *         .description("Project description")
 *         .status("ACTIVE")
 *         .build();
 * 
 * UUID projectId = projectCreateCommand.execute(input);
 * }
 * </pre>
 */
@Service
@Slf4j
public class ProjectCreateCommand extends BaseCrudCreateCommandV2<Project, ProjectCreateInput, UUID> {

    private final ProjectRepository projectRepository;

    public ProjectCreateCommand(ProjectRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
        this.projectRepository = repository;
    }

    /**
     * Define validation rules using YAVI
     */
    @Override
    protected ValidatorBuilder<ProjectCreateInput> getValidatorBuilder(CommandHolder<ProjectCreateInput> holder) {
        return ProjectValidation.create(projectRepository);
    }

    /**
     * Set default values before save
     */
    @Override
    protected Project beforeSave(Project entity, ProjectCreateInput input, CommandHolder<ProjectCreateInput> holder) {
        // Set default values if not provided in input
        if (input.getActive() == null) {
            entity.setActive(true);
        }
        if (input.getStatus() == null || input.getStatus().isBlank()) {
            entity.setStatus("ACTIVE");
        }
        return entity;
    }

    /**
     * After save hook - URN is already built by @PostPersist callback in entity
     */
    @Override
    protected void afterSave(Project entity, ProjectCreateInput input, CommandHolder<ProjectCreateInput> holder) {
        log.info("Created project with ID: {} and URN: {}", entity.getId(), entity.getUrn());
    }
}
