-- liquibase formatted sql
-- changeset system:v1.0-fix-003-add-missing-columns-to-projects
-- comment: Add missing columns (status, active, created_by, updated_by) to projects table

-- Add status column
ALTER TABLE projects ADD COLUMN IF NOT EXISTS status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE';

-- Add active column for soft delete
ALTER TABLE projects ADD COLUMN IF NOT EXISTS active BOOLEAN NOT NULL DEFAULT true;

-- Add created_by and updated_by audit columns
ALTER TABLE projects ADD COLUMN IF NOT EXISTS created_by VARCHAR(255);
ALTER TABLE projects ADD COLUMN IF NOT EXISTS updated_by VARCHAR(255);

-- rollback ALTER TABLE projects DROP COLUMN IF EXISTS status; ALTER TABLE projects DROP COLUMN IF EXISTS active; ALTER TABLE projects DROP COLUMN IF EXISTS created_by; ALTER TABLE projects DROP COLUMN IF EXISTS updated_by;
