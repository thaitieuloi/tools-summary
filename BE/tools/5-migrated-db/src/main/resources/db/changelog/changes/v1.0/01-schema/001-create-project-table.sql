-- liquibase formatted sql
-- changeset system:v1.0-schema-001-create-project-table
-- comment: Create project table structure

CREATE TABLE IF NOT EXISTS project (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    urn VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- rollback DROP TABLE IF EXISTS project CASCADE;
