# 03-data - Initial and Reference Data

This folder contains SQL files for inserting initial or reference data.

## 📌 Important: Use SQL Files

For data migrations, **always use `.sql` files** instead of `.yaml` files because:
- ✅ Easier to write and read
- ✅ Can test directly in database client
- ✅ Better for bulk inserts
- ✅ Supports complex data transformations

## 📝 SQL File Format

All SQL files must start with Liquibase headers:

```sql
-- liquibase formatted sql
-- changeset author:changeset-id
-- comment: Description of what this does

-- Your SQL statements here

-- rollback Your rollback SQL here
```

## 📚 Examples

### Example 1: Simple Insert

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-data-001-insert-roles
-- comment: Insert default user roles

INSERT INTO role (name, description) VALUES
    ('ADMIN', 'System administrator'),
    ('USER', 'Regular user'),
    ('GUEST', 'Guest user')
ON CONFLICT (name) DO NOTHING;

-- rollback DELETE FROM role WHERE name IN ('ADMIN', 'USER', 'GUEST');
```

### Example 2: Using CSV (recommended for bulk data)

**File: 002-import-countries.sql**
```sql
-- liquibase formatted sql
-- changeset admin:v1.0-data-002-import-countries
-- comment: Import countries from CSV

-- Liquibase will automatically load from the CSV file
-- Create CSV file: 002-import-countries.csv

-- rollback DELETE FROM country;
```

**File: 002-import-countries.csv**
```csv
code,name,region
US,United States,North America
VN,Vietnam,Asia
JP,Japan,Asia
```

### Example 3: Insert with SELECT

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-data-003-copy-legacy-data
-- comment: Copy data from legacy table

INSERT INTO new_table (id, name, status)
SELECT id, name, 'ACTIVE'
FROM legacy_table
WHERE deleted_at IS NULL;

-- rollback DELETE FROM new_table WHERE id IN (SELECT id FROM legacy_table WHERE deleted_at IS NULL);
```

### Example 4: Context-Specific Data

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-data-004-dev-users context:dev
-- comment: Insert test users (only for development)

INSERT INTO user (username, password, role) VALUES
    ('test_admin', 'test123', 'ADMIN'),
    ('test_user', 'test123', 'USER')
ON CONFLICT (username) DO NOTHING;

-- rollback DELETE FROM user WHERE username LIKE 'test_%';
```

## ⚠️ Best Practices

1. **Use ON CONFLICT**: Always handle conflicts gracefully
   ```sql
   INSERT INTO table (id, name) VALUES (1, 'Test')
   ON CONFLICT (id) DO NOTHING;
   ```

2. **Idempotent Inserts**: Make sure running twice doesn't create duplicates
   ```sql
   INSERT INTO table (unique_key, value)
   SELECT 'key1', 'value1'
   WHERE NOT EXISTS (SELECT 1 FROM table WHERE unique_key = 'key1');
   ```

3. **Always Include Rollback**: Even for data
   ```sql
   -- rollback DELETE FROM table WHERE id = 123;
   ```

4. **Use Contexts**: Separate dev/prod data
   ```sql
   -- changeset author:id context:dev
   ```

5. **Timestamps**: Use database functions
   ```sql
   INSERT INTO table (created_at) VALUES (CURRENT_TIMESTAMP);
   ```

## 🚫 Don't Do This

❌ **Don't use hard-coded IDs that might conflict**
```sql
-- BAD
INSERT INTO user (id, name) VALUES (1, 'Admin');
```

✅ **Better: Use sequences or let database generate**
```sql
-- GOOD
INSERT INTO user (name) VALUES ('Admin')
RETURNING id;
```

❌ **Don't insert environment-specific data without context**
```sql
-- BAD - will insert in production too!
INSERT INTO config (key, value) VALUES ('debug_mode', 'true');
```

✅ **Better: Use context**
```sql
-- liquibase formatted sql
-- changeset admin:id context:dev
-- GOOD - only in dev
INSERT INTO config (key, value) VALUES ('debug_mode', 'true');
```
