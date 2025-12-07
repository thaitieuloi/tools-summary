-- liquibase formatted sql
-- changeset system:v1.0-fix-001-rename-project-to-projects
-- comment: Rename project table to projects to match entity

ALTER TABLE IF EXISTS project RENAME TO projects;

-- rollback ALTER TABLE IF EXISTS projects RENAME TO project;
