package com.ttl.tool.core.command.project;

import am.ik.yavi.builder.ValidatorBuilder;
import com.ttl.common.core.command.CommandHolder;
import com.ttl.common.core.command.crud.BaseCrudUpdateCommandV2;
import com.ttl.common.core.mapper.EntityMapper;
import com.ttl.tool.core.dto.input.ProjectUpdateInput;
import com.ttl.tool.core.validation.ProjectValidation;
import com.ttl.tool.domain.entity.Project;
import com.ttl.tool.domain.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Command for updating an existing Project.
 * 
 * Features:
 * - Auto-mapping from ProjectUpdateInput to Project entity
 * - Input validation using YAVI
 * - Business logic hooks (beforeUpdate, beforeSave, afterSave)
 * - URN field is automatically updated via @PostUpdate callback in entity
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * ProjectUpdateInput input = ProjectUpdateInput.builder()
 *         .id(projectId)
 *         .name("Updated Project Name")
 *         .description("Updated description")
 *         .status("COMPLETED")
 *         .build();
 * 
 * Project updatedProject = projectUpdateCommand.execute(input);
 * }
 * </pre>
 */
@Service
@Slf4j
public class ProjectUpdateCommand extends BaseCrudUpdateCommandV2<Project, ProjectUpdateInput, UUID> {

    public ProjectUpdateCommand(ProjectRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
    }

    @Override
    protected UUID extractId(ProjectUpdateInput input) {
        return input.getId();
    }

    /**
     * Define validation rules using YAVI
     */
    @Override
    protected ValidatorBuilder<ProjectUpdateInput> getValidatorBuilder(CommandHolder<ProjectUpdateInput> holder) {
        return ProjectValidation.update();
    }

    /**
     * After save hook - URN is already updated by @PostUpdate callback in entity
     */
    @Override
    protected void afterSave(Project entity, ProjectUpdateInput input, CommandHolder<ProjectUpdateInput> holder) {
        log.info("Updated project with ID: {} and URN: {}", entity.getId(), entity.getUrn());
    }
}
