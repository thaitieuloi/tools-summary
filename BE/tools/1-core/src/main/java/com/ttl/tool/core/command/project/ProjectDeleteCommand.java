package com.ttl.tool.core.command.project;

import com.ttl.common.core.command.crud.BaseCrudDeleteCommand;
import com.ttl.tool.domain.entity.Project;
import com.ttl.tool.domain.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Command for deleting a Project.
 * 
 * Features:
 * - Finds project by ID
 * - Deletes project from database
 * - Business logic hooks (beforeDelete, afterDelete)
 * 
 * Example usage:
 * 
 * <pre>
 * {@code
 * UUID projectId = UUID.fromString("...");
 * projectDeleteCommand.execute(projectId);
 * }
 * </pre>
 */
@Service
@Slf4j
public class ProjectDeleteCommand extends BaseCrudDeleteCommand<Project, UUID> {

    public ProjectDeleteCommand(ProjectRepository repository) {
        super(repository);
    }
}
