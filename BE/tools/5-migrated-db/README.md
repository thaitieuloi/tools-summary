# Database Migration Module

This module handles database schema migrations using Liquibase.

## 📋 Overview

The `5-migrated-db` module is a **standalone migration tool** that:
- ✅ Runs database migrations independently from the main application
- ✅ Uses Liquibase for version-controlled schema changes
- ✅ Can be executed before deployment or as a separate step
- ✅ Keeps the main API module clean without migration dependencies

## 🏗️ Architecture

```
5-migrated-db/
├── src/main/
│   ├── java/
│   │   └── com/ttl/tool/migration/
│   │       └── MigrationApplication.java
│   └── resources/
│       ├── application.yml
│       └── db/changelog/
│           ├── db.changelog-master.yaml      # Includes index files only
│           └── changes/
│               └── v1.0/
│                   ├── 01-schema/           
│                   │   ├── index.yaml        # Lists all SQL files in folder
│                   │   ├── 001-create-project-table.sql ⭐
│                   │   └── 002-create-user-table.sql ⭐
│                   ├── 02-constraints/
│                   │   ├── index.yaml
│                   │   └── *.sql ⭐
│                   ├── 03-data/
│                   │   ├── index.yaml
│                   │   └── *.sql ⭐
│                   ├── 04-function/
│                   │   ├── index.yaml
│                   │   └── *.sql ⭐
│                   └── 05-trigger/
│                       ├── index.yaml
│                       └── *.sql ⭐
├── migrate.bat        # Windows migration script
├── migrate.sh         # Linux/Mac migration script
└── build.gradle
```

### Changelog Organization

**All migration files are SQL** - no more YAML for changesets!

Each version folder contains:
1. **01-schema** - Table structures (SQL files)
2. **02-constraints** - Indexes, PKs, FKs (SQL files)
3. **03-data** - Initial/reference data (SQL files)
4. **04-function** - Database functions (SQL files)
5. **05-trigger** - Database triggers (SQL files)

Each folder has an `index.yaml` that includes all SQL files in that folder.

The master changelog (`db.changelog-master.yaml`) only includes the index files:
```yaml
databaseChangeLog:
  - include: v1.0/01-schema/index.yaml
  - include: v1.0/02-constraints/index.yaml
  - include: v1.0/03-data/index.yaml
  - include: v1.0/04-function/index.yaml
  - include: v1.0/05-trigger/index.yaml
```

**Benefits:**
- ✅ Simple: All migrations in pure SQL
- ✅ Organized: Each folder manages its own files
- ✅ Scalable: Add new files by updating folder's index.yaml
- ✅ Clean: Master changelog stays minimal

## 🚀 Usage

### Method 1: Using Gradle Task (Recommended for Development)

```bash
# From project root
./gradlew :migrated-db:migrate

# With specific profile
./gradlew :migrated-db:migrate -Pprofile=production
```

### Method 2: Using Bootable JAR (Recommended for Production)

```bash
# Build the migration JAR
./gradlew :migrated-db:bootJar

# Run the migration
java -jar 5-migrated-db/build/libs/tool-migration.jar

# With specific profile
java -jar 5-migrated-db/build/libs/tool-migration.jar --spring.profiles.active=production
```

### Method 3: Direct Spring Boot Run

Edit `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/tool
    username: postgres
    password: 123456
```

### Environment-Specific Configuration

Create profile-specific files:
- `application-dev.yml`
- `application-staging.yml`
- `application-production.yml`

## 📊 Best Practices

### General Guidelines

1. **Version Control**: Each major feature should have its own version folder (v1.0, v1.1, etc.)
2. **Naming Convention**: Use `XXX-descriptive-name.yaml` format (e.g., `001-create-user-table.yaml`)
3. **Rollback**: Always define rollback for each changeSet
4. **Idempotency**: Migrations should be safe to run multiple times
5. **Testing**: Test migrations on a copy of production data before deployment

### Folder-Specific Guidelines

#### 01-schema (Use YAML)
- Create tables and columns only
- Don't include constraints (except NOT NULL)
- Don't include indexes
- Keep it simple and focused
- **Format**: YAML for Liquibase structure

```yaml
# ✅ Good - Use YAML for schema structure
databaseChangeLog:
  - changeSet:
      id: v1.0-schema-001-create-user-table
      changes:
        - createTable:
            tableName: user
            columns:
              - column:
                  name: id
                  type: bigint
```

#### 02-constraints (Use YAML)
- Add all constraints after schema is created
- Include: PKs, FKs, unique constraints, check constraints
- Add indexes for foreign keys and frequently queried columns
- **Format**: YAML for Liquibase structure

```yaml
# ✅ Good - Use YAML for constraints
- addPrimaryKey:
    tableName: user
    columnNames: id
- addForeignKeyConstraint:
    baseTableName: order
    baseColumnNames: user_id
    referencedTableName: user
```

#### 03-data (Use SQL) ⭐
- Insert reference/lookup data
- Insert default configurations
- Use CSV for bulk data
- Make sure data is environment-agnostic or use contexts
- **Format**: SQL files (NOT YAML)

```sql
-- ✅ Good - Use SQL for data inserts
-- liquibase formatted sql
-- changeset admin:v1.0-data-001-insert-roles
-- comment: Insert default roles

INSERT INTO role (name, description) VALUES
    ('ADMIN', 'Administrator'),
    ('USER', 'Regular user')
ON CONFLICT (name) DO NOTHING;

-- rollback DELETE FROM role WHERE name IN ('ADMIN', 'USER');
```

**Why SQL?**
- Easier to write and read
- Can test directly in database client
- Better for bulk inserts
- Supports complex queries

#### 04-function (Use SQL) ⭐
- Create reusable database functions
- Always specify `dbms:postgresql`
- Include complete function definition
- **Format**: SQL files (NOT YAML)

```sql
-- ✅ Good - Use SQL for functions
-- liquibase formatted sql
-- changeset admin:v1.0-function-001 dbms:postgresql

CREATE OR REPLACE FUNCTION update_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS update_timestamp();
```

**Why SQL?**
- Better syntax highlighting
- Native SQL syntax
- Easier to debug
- Can copy/paste from database client

#### 05-trigger (Use SQL) ⭐
- Create triggers that depend on functions
- Always specify BEFORE or AFTER
- Document the trigger's purpose
- **Format**: SQL files (NOT YAML)

```sql
-- ✅ Good - Use SQL for triggers
-- liquibase formatted sql  
-- changeset admin:v1.0-trigger-001 dbms:postgresql

CREATE TRIGGER trigger_user_timestamp
BEFORE UPDATE ON user
FOR EACH ROW
EXECUTE FUNCTION update_timestamp();

-- rollback DROP TRIGGER IF EXISTS trigger_user_timestamp ON user;
```

**Why SQL?**
- Clear and concise
- Standard SQL syntax
- Easy to test

### Execution Order

The folder structure ensures proper execution order:
1. Schema first (tables must exist)
2. Constraints second (tables must exist)
3. Data third (schema + constraints ready)
4. Functions fourth (can reference tables)
5. Triggers last (depends on functions + tables)

## 🔍 Troubleshooting

### Issue: "changelog does not exist"
- Ensure the path in `application.yml` matches your file structure
- Check that files are in `src/main/resources/db/changelog/`

### Issue: "Connection refused"
- Verify database is running
- Check connection credentials in `application.yml`

### Issue: "Checksum validation failed"
- Don't modify existing changesets after they've been run
- If needed, create a new changeset to fix issues

## 🎯 Integration with CI/CD

### Example GitLab CI

```yaml
stages:
  - migrate
  - deploy

migrate:
  stage: migrate
  script:
    - ./gradlew :migrated-db:bootJar
    - java -jar 5-migrated-db/build/libs/tool-migration.jar
  only:
    - main

deploy:
  stage: deploy
  script:
    - ./gradlew :api:bootJar
    - # Deploy API
  needs:
    - migrate
```

### Example GitHub Actions

```yaml
- name: Run Database Migration
  run: |
    ./gradlew :migrated-db:migrate
    
- name: Build and Deploy API
  run: |
    ./gradlew :api:bootJar
```

## ✅ Verification

After running migration, verify:

```bash
# Check migration status
./gradlew :migrated-db:migrate

# Verify in database
psql -U postgres -d tool -c "SELECT * FROM databasechangelog;"
```
