package com.ttl.tool.domain.repository;

import com.ttl.tool.domain.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Project entity
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

    /**
     * Find project by name
     */
    Optional<Project> findByName(String name);

    /**
     * Check if project exists by name
     */
    boolean existsByName(String name);

    /**
     * Find all active projects
     */
    Page<Project> findByActiveTrue(Pageable pageable);

    /**
     * Find projects by status
     */
    Page<Project> findByStatus(String status, Pageable pageable);
}
