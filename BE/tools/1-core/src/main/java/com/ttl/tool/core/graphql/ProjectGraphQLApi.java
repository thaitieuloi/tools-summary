package com.ttl.tool.core.graphql;

import com.ttl.tool.core.command.project.ProjectCreateCommand;
import com.ttl.tool.core.command.project.ProjectDeleteCommand;
import com.ttl.tool.core.command.project.ProjectUpdateCommand;
import com.ttl.tool.core.dto.input.ProjectCreateInput;
import com.ttl.tool.core.dto.input.ProjectUpdateInput;
import com.ttl.tool.domain.entity.Project;
import com.ttl.tool.domain.repository.ProjectRepository;
import com.ttl.tool.shared.dto.ProjectSearchInput;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL API for Project operations.
 * Uses Command Pattern to execute business logic.
 * 
 * Architecture:
 * GraphQL API → Command → Repository
 * 
 * Features:
 * - CRUD operations for projects
 * - Search and filter capabilities
 * - URN field is automatically populated in responses
 * 
 * Example GraphQL queries and mutations:
 * 
 * Query:
 * {
 * projectFindById(id: "uuid-here") {
 * id
 * name
 * description
 * status
 * urn
 * createdAt
 * }
 * }
 * 
 * {
 * projectSearch(filter: {status: "ACTIVE"}, page: 0, size: 10) {
 * id
 * name
 * urn
 * }
 * }
 * 
 * Mutation:
 * mutation {
 * projectCreate(input: {
 * name: "New Project"
 * description: "Description"
 * status: "ACTIVE"
 * })
 * }
 * 
 * mutation {
 * projectUpdate(id: "uuid-here", input: {
 * name: "Updated Name"
 * status: "COMPLETED"
 * }) {
 * id
 * name
 * urn
 * }
 * }
 * 
 * mutation {
 * projectDelete(id: "uuid-here")
 * }
 */
@Component
@GraphQLApi
@RequiredArgsConstructor
public class ProjectGraphQLApi {

    // Commands for mutations
    private final ProjectCreateCommand projectCreateCommand;
    private final ProjectUpdateCommand projectUpdateCommand;
    private final ProjectDeleteCommand projectDeleteCommand;

    // Repository for queries (queries don't need commands)
    private final ProjectRepository projectRepository;

    /**
     * Query: Get a single project by ID
     * The URN field will be automatically populated by the entity's @PostLoad
     * callback
     */
    @GraphQLQuery(description = "Get project by ID")
    public Project projectFindById(@GraphQLArgument(name = "id") String id) {
        return projectRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    /**
     * Query: Get a project by name
     */
    @GraphQLQuery(description = "Get project by name")
    public Project projectFindByName(@GraphQLArgument(name = "name") String name) {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Project not found with name: " + name));
    }

    /**
     * Query: Search projects with pagination
     * The URN field will be automatically populated for each project
     */
    @GraphQLQuery(description = "Search projects with filters")
    public List<Project> projectSearch(
            @GraphQLArgument(name = "filter") ProjectSearchInput filter,
            @GraphQLArgument(name = "page") Integer page,
            @GraphQLArgument(name = "size") Integer size) {

        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Project> projectPage;

        if (filter != null) {
            // Apply filters if provided
            if (filter.getStatus() != null && !filter.getStatus().isBlank()) {
                projectPage = projectRepository.findByStatus(filter.getStatus(), pageable);
            } else if (Boolean.TRUE.equals(filter.getActive())) {
                projectPage = projectRepository.findByActiveTrue(pageable);
            } else {
                projectPage = projectRepository.findAll(pageable);
            }
        } else {
            projectPage = projectRepository.findAll(pageable);
        }

        return projectPage.getContent();
    }

    /**
     * Query: Get all active projects
     */
    @GraphQLQuery(description = "Get all active projects")
    public List<Project> projectFindAllActive(
            @GraphQLArgument(name = "page") Integer page,
            @GraphQLArgument(name = "size") Integer size) {

        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return projectRepository.findByActiveTrue(pageable).getContent();
    }

    /**
     * Mutation: Create a new project
     * Uses ProjectCreateCommand to execute the business logic
     * The URN will be automatically generated as "tools:project:{id}"
     * 
     * @return The ID of the created project
     */
    @GraphQLMutation(name = "projectCreate", description = "Create a new project")
    public UUID projectCreate(@GraphQLArgument(name = "input") ProjectCreateInput input) {
        // Execute the command and return the created project ID
        return projectCreateCommand.execute(input);
    }

    /**
     * Mutation: Update an existing project
     * Uses ProjectUpdateCommand to execute the business logic
     * The URN will be automatically updated via @PostUpdate callback
     * 
     * @return The updated project entity
     */
    @GraphQLMutation(description = "Update an existing project")
    public Project projectUpdate(
            @GraphQLArgument(name = "id") String id,
            @GraphQLArgument(name = "input") ProjectUpdateInput input) {

        // Set the ID in the input
        input.setId(UUID.fromString(id));

        // Execute the command and return the updated project entity
        return projectUpdateCommand.execute(input);
    }

    /**
     * Mutation: Delete a project
     * Uses ProjectDeleteCommand to execute the business logic
     * 
     * @return true if deletion was successful
     */
    @GraphQLMutation(description = "Delete a project")
    public Boolean projectDelete(@GraphQLArgument(name = "id") String id) {
        // Execute the command and return success status
        projectDeleteCommand.execute(UUID.fromString(id));
        return true;
    }
}
