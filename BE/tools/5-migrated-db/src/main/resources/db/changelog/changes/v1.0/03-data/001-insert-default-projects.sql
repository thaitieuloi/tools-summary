-- liquibase formatted sql
-- changeset system:v1.0-data-001-insert-default-projects
-- comment: Insert default project records

-- Insert default projects
INSERT INTO project (name, description, urn, created_at, updated_at)
VALUES 
    ('Default Project', 'Initial project created by migration', 'tools:project:default', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Sample Project', 'Sample project for testing', 'tools:project:sample', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (urn) DO NOTHING;

-- rollback DELETE FROM project WHERE urn IN ('tools:project:default', 'tools:project:sample');
