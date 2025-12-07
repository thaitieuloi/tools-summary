-- liquibase formatted sql
-- changeset system:v1.0-constraints-001-add-project-indexes
-- comment: Add indexes for project table

CREATE INDEX IF NOT EXISTS idx_project_urn ON project(urn);
CREATE INDEX IF NOT EXISTS idx_project_name ON project(name);
CREATE INDEX IF NOT EXISTS idx_project_created_at ON project(created_at DESC);

-- rollback DROP INDEX IF EXISTS idx_project_urn;
-- rollback DROP INDEX IF EXISTS idx_project_name;
-- rollback DROP INDEX IF EXISTS idx_project_created_at;
