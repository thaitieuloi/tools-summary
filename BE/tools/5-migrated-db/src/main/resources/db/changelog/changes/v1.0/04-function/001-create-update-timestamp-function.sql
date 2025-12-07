-- liquibase formatted sql
-- changeset system:v1.0-function-001-create-update-timestamp dbms:postgresql  splitStatements:false
-- comment: Create function to automatically update updated_at timestamp

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS update_updated_at_column();
