-- liquibase formatted sql
-- changeset system:v1.0-fix-002-change-project-id-to-uuid
-- comment: Change project id column from BIGSERIAL to UUID to match entity

-- First drop the old id column (this will cascade to indexes/constraints)  
ALTER TABLE projects DROP COLUMN id;

-- Add new id column with UUID type
ALTER TABLE projects ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- rollback ALTER TABLE projects DROP COLUMN id; ALTER TABLE projects ADD COLUMN id BIGSERIAL PRIMARY KEY;
