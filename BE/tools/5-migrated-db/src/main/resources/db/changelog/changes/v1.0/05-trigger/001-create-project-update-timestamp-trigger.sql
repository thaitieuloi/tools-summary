-- liquibase formatted sql
-- changeset system:v1.0-trigger-001-project-update-timestamp dbms:postgresql splitStatements:false
-- comment: Create trigger to automatically update project.updated_at

CREATE TRIGGER trigger_update_project_timestamp
BEFORE UPDATE ON project
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- rollback DROP TRIGGER IF EXISTS trigger_update_project_timestamp ON project;
