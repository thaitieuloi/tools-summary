# 🗄️ Database Migration Guide - UPDATED

## ✨ Cấu Trúc Mới (All SQL!)

**Tất cả migration files giờ dùng SQL** - không còn YAML nữa (ngoại trừ file index)!

```
v1.0/
├── 01-schema/
│   ├── index.yaml                    # File index (include các SQL)
│   └── 001-create-project-table.sql  # ⭐ SQL file
├── 02-constraints/
│   ├── index.yaml
│   └── 001-add-project-indexes.sql   # ⭐ SQL file
├── 03-data/
│   ├── index.yaml
│   └── 001-insert-default-projects.sql # ⭐ SQL file
├── 04-function/
│   ├── index.yaml
│   └── 001-create-function.sql       # ⭐ SQL file
└── 05-trigger/
    ├── index.yaml
    └── 001-create-trigger.sql        # ⭐ SQL file
```

**Master changelog chỉ include các file index:**
```yaml
# db.changelog-master.yaml
databaseChangeLog:
  - include:
      file: db/changelog/changes/v1.0/01-schema/index.yaml
  - include:
      file: db/changelog/changes/v1.0/02-constraints/index.yaml
  # ...
```

## 🚀 Quick Start

### Windows
```bash
cd BE\tools\5-migrated-db
migrate.bat
```

### Linux/Mac
```bash
cd BE/tools/5-migrated-db
./migrate.sh
```

## 📝 Tạo Migration Mới

### Bước 1: Tạo file SQL
```bash
# Ví dụ: Tạo table mới
# File: v1.0/01-schema/002-create-user-table.sql
```

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-schema-002-create-user-table
-- comment: Create user table

CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- rollback DROP TABLE IF EXISTS users CASCADE;
```

### Bước 2: Add vào file index.yaml
```yaml
# File: v1.0/01-schema/index.yaml
databaseChangeLog:
  # Existing
  - include:
      file: db/changelog/changes/v1.0/01-schema/001-create-project-table.sql
      
  # New ⭐
  - include:
      file: db/changelog/changes/v1.0/01-schema/002-create-user-table.sql
```

### Bước 3: Run migration
```bash
migrate.bat
```

**Xong!** Master changelog tự động pick up từ file index.

## 📋 SQL File Template

```sql
-- liquibase formatted sql
-- changeset author:unique-id [dbms:postgresql] [context:dev]
-- comment: What this migration does

-- Your SQL statements here
CREATE TABLE ...;
INSERT INTO ...;
CREATE FUNCTION ...;

-- rollback statements (required!)
-- rollback DROP TABLE ...;
```

## ✅ Advantages của cấu trúc này

1. ✅ **Tất cả dùng SQL** - không cần học YAML syntax
2. ✅ **Test được ngay** - copy/paste vào psql
3. ✅ **Master changelog gọn** - chỉ include index files
4. ✅ **Dễ organize** - mỗi folder tự quản lý SQL files của mình
5. ✅ **Scalable** - thêm file mới chỉ cần update index.yaml của folder đó

## 🎯 Best Practices

### ✅ DO
- ✅ Tất cả migration dùng **SQL files**
- ✅ Đặt tên rõ ràng: `XXX-descriptive-name.sql`
- ✅ Luôn có `-- rollback`
- ✅ Sử dụng `IF NOT EXISTS` / `IF EXISTS`
- ✅ Add vào `index.yaml` của folder
- ✅ Test trên dev database trước

### ❌ DON'T
- ❌ Không modify file SQL đã chạy
- ❌ Không skip version numbers
- ❌ Không quên rollback statements

For detailed info: `5-migrated-db/README.md`
