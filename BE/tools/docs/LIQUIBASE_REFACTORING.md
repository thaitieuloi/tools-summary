# Liquibase Refactoring Summary

**Date**: 2025-12-06
**Objective**: Separate Liquibase migration files into dedicated modules (`migrated-api`, `migrated-db`) similar to `notification` service.

## Changes Made

1.  **New Modules Created**:
    *   `5-migrated-db`: Contains database migration scripts (Liquibase changelogs).
    *   `5-migrated-api`: Placeholder module for future API migration logic (currently empty structure).

2.  **Migration Files Moved**:
    *   Moved `001-create-projects-table.sql` from `5-api` to `5-migrated-db/src/main/resources/db/changelog/001-projects/`.
    *   Created `changelog.xml` in `5-migrated-db` to include the SQL script.
    *   Created `changelog-master.xml` in `5-migrated-db`.

3.  **Configuration Updates**:
    *   **settings.gradle**: Included `migrated-api` and `migrated-db`.
    *   **5-api/build.gradle**: Added dependency `implementation project(':migrated-db')`.
    *   **application.yml**: Updated `spring.liquibase.change-log` path to `classpath:/db/changelog/changelog-master.xml`.

4.  **Clean Up**:
    *   Removed `db/changelog` directory from `5-api`.

## Module Structure

```
tools/
├── 5-migrated-db/
│   ├── build.gradle
│   └── src/main/resources/db/changelog/
│       ├── changelog-master.xml
│       └── 001-projects/
│           ├── changelog.xml
│           └── 001-create-projects-table.sql
├── 5-migrated-api/
│   └── build.gradle
└── 5-api/
    └── build.gradle (depends on :migrated-db)
```
