# 🚀 Quick Reference - Liquibase Migration

## Cấu Trúc (All SQL!)

```
v1.0/
├── 01-schema/
│   ├── index.yaml          # Include list
│   └── *.sql              # ⭐ All SQL
├── 02-constraints/
│   ├── index.yaml
│   └── *.sql              # ⭐ All SQL
├── 03-data/
│   ├── index.yaml
│   └── *.sql              # ⭐ All SQL
├── 04-function/
│   ├── index.yaml
│   └── *.sql              # ⭐ All SQL
└── 05-trigger/
    ├── index.yaml
    └── *.sql              # ⭐ All SQL
```

## Commands

```bash
# Run migration
migrate.bat                 # Windows
./migrate.sh               # Linux/Mac
./gradlew :migrated-db:migrate

# Start API
./gradlew :api:bootRun
```

## SQL Template

```sql
-- liquibase formatted sql
-- changeset author:id
-- comment: Description

CREATE TABLE ...;

-- rollback DROP TABLE ...;
```

## Add New Migration

1. Create SQL file in folder
2. Add to folder's `index.yaml`
3. Run `migrate.bat`

Example:
```yaml
# 01-schema/index.yaml
databaseChangeLog:
  - include:
      file: db/changelog/changes/v1.0/01-schema/002-new-table.sql
```

## Master Changelog

```yaml
# db.changelog-master.yaml
databaseChangeLog:
  - include: v1.0/01-schema/index.yaml
  - include: v1.0/02-constraints/index.yaml
  - include: v1.0/03-data/index.yaml
  - include: v1.0/04-function/index.yaml
  - include: v1.0/05-trigger/index.yaml
```

**Clean & Simple!** 🎯
