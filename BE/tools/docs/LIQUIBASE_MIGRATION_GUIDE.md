# 📋 Tổng Kết: Cấu Hình Liquibase Migration

## ✅ Vấn Đề Đã Giải Quyết

### Trước đây:
❌ Liquibase chạy mỗi khi start API → chậm  
❌ Changelog file không tồn tại → lỗi startup  
❌ Không tách biệt migration và runtime  
❌ Không có cấu trúc rõ ràng cho changelog  

### Hiện tại:
✅ Migration chạy độc lập bằng module riêng  
✅ API start nhanh, không cần Liquibase  
✅ Cấu trúc changelog có tổ chức theo best practices  
✅ Sử dụng SQL files cho data, functions, triggers  

---

## 🏗️ Kiến Trúc Mới

### Module Structure
```
5-migrated-db/              # Module migration độc lập
├── migrate.bat            # Script Windows
├── migrate.sh             # Script Linux/Mac  
├── README.md             # Hướng dẫn chi tiết
└── src/main/
    ├── java/
    │   └── MigrationApplication.java
    └── resources/
        ├── application.yml
        └── db/changelog/
            ├── db.changelog-master.yaml
            └── changes/
                └── v1.0/
                    ├── 01-schema/        # YAML files
                    ├── 02-constraints/   # YAML files
                    ├── 03-data/         # SQL files ⭐
                    ├── 04-function/     # SQL files ⭐
                    └── 05-trigger/      # SQL files ⭐

5-api/                      # Module API chính
└── Liquibase: DISABLED ✅
```

### Changelog Organization

Mỗi version có 5 folders theo thứ tự thực thi:

| Folder | Purpose | File Format | Example |
|--------|---------|-------------|---------|
| **01-schema** | Tables, Columns | YAML | `001-create-user-table.yaml` |
| **02-constraints** | PKs, FKs, Indexes | YAML | `001-add-user-indexes.yaml` |
| **03-data** | Initial Data | SQL ⭐ | `001-insert-roles.sql` |
| **04-function** | DB Functions | SQL ⭐ | `001-update-timestamp.sql` |
| **05-trigger** | DB Triggers | SQL ⭐ | `001-user-timestamp.sql` |

---

## 🚀 Sử Dụng

### Development Workflow

```bash
# 1. Chạy migration TRƯỚC
cd BE\tools\5-migrated-db
migrate.bat

# 2. Start API SAU
cd ..\5-api
..\..\gradlew.bat bootRun
```

### Production Deployment

```bash
# 1. Build migration JAR
./gradlew :migrated-db:bootJar

# 2. Run migration
java -jar 5-migrated-db/build/libs/tool-migration.jar

# 3. Deploy API
./gradlew :api:bootJar
java -jar 5-api/build/libs/tool-api.jar
```

---

## 📝 Tạo Migration Mới

### Quy Tắc Chung

1. **Xác định loại thay đổi** → Chọn folder phù hợp
2. **Đặt tên file** → `XXX-descriptive-name.yaml|sql`
3. **Viết migration** → Theo format của folder
4. **Include vào master** → Update `db.changelog-master.yaml`
5. **Test** → Chạy migration trên dev database

### Ví Dụ Cụ Thể

#### 1. Thêm Table Mới (01-schema)

**File:** `v1.0/01-schema/003-create-order-table.yaml`
```yaml
databaseChangeLog:
  - changeSet:
      id: v1.0-schema-003-create-order-table
      author: admin
      changes:
        - createTable:
            tableName: orders
            columns:
              - column:
                  name: id
                  type: bigint
                  autoIncrement: true
```

#### 2. Thêm Index (02-constraints)

**File:** `v1.0/02-constraints/003-add-order-indexes.yaml`
```yaml
databaseChangeLog:
  - changeSet:
      id: v1.0-constraints-003-order-indexes
      changes:
        - createIndex:
            indexName: idx_order_user_id
            tableName: orders
            columns:
              - column:
                  name: user_id
```

#### 3. Insert Data (03-data) ⭐ SQL

**File:** `v1.0/03-data/002-insert-default-settings.sql`
```sql
-- liquibase formatted sql
-- changeset admin:v1.0-data-002-settings
-- comment: Insert default application settings

INSERT INTO settings (key, value, description) VALUES
    ('max_upload_size', '10485760', 'Max file upload size in bytes'),
    ('session_timeout', '3600', 'Session timeout in seconds')
ON CONFLICT (key) DO NOTHING;

-- rollback DELETE FROM settings WHERE key IN ('max_upload_size', 'session_timeout');
```

#### 4. Create Function (04-function) ⭐ SQL

**File:** `v1.0/04-function/002-calculate-total.sql`
```sql
-- liquibase formatted sql
-- changeset admin:v1.0-function-002-calculate dbms:postgresql
-- comment: Calculate order total

CREATE OR REPLACE FUNCTION calculate_order_total(order_id bigint)
RETURNS decimal AS $$
DECLARE
    total decimal;
BEGIN
    SELECT SUM(price * quantity) INTO total
    FROM order_item
    WHERE order_id = $1;
    
    RETURN COALESCE(total, 0);
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS calculate_order_total(bigint);
```

#### 5. Create Trigger (05-trigger) ⭐ SQL

**File:** `v1.0/05-trigger/002-order-timestamp.sql`
```sql
-- liquibase formatted sql
-- changeset admin:v1.0-trigger-002-order dbms:postgresql
-- comment: Auto-update order timestamp

CREATE TRIGGER trigger_update_order_timestamp
BEFORE UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- rollback DROP TRIGGER IF EXISTS trigger_update_order_timestamp ON orders;
```

#### 6. Update Master Changelog

**File:** `db.changelog-master.yaml`
```yaml
databaseChangeLog:
  # ... existing entries ...
  
  # New entries
  - include:
      file: db/changelog/changes/v1.0/01-schema/003-create-order-table.yaml
  - include:
      file: db/changelog/changes/v1.0/02-constraints/003-add-order-indexes.yaml
  - include:
      file: db/changelog/changes/v1.0/03-data/002-insert-default-settings.sql
  - include:
      file: db/changelog/changes/v1.0/04-function/002-calculate-total.sql
  - include:
      file: db/changelog/changes/v1.0/05-trigger/002-order-timestamp.sql
```

---

## 🎯 Best Practices

### ✅ DO

- ✅ **Sử dụng SQL files** cho data, functions, triggers
- ✅ **Sử dụng YAML files** cho schema, constraints
- ✅ **Luôn có rollback** cho mọi changeset
- ✅ **Test migration** trên database clone trước
- ✅ **Đặt tên rõ ràng** → `001-create-user-table.yaml`
- ✅ **Sử dụng ON CONFLICT** để idempotent
- ✅ **Context cho dev data** → `context:dev`
- ✅ **Version theo feature** → v1.0, v1.1, v2.0

### ❌ DON'T

- ❌ **Không modify** changeset đã chạy
- ❌ **Không hard-code IDs** có thể conflict
- ❌ **Không quên rollback**
- ❌ **Không insert prod data** vào dev context
- ❌ **Không skip version numbers**

---

## 📚 Tài Liệu Tham Khảo

### Trong Project
- `5-migrated-db/README.md` - Hướng dẫn chi tiết module migration
- `5-migrated-db/src/main/resources/db/changelog/changes/v1.0/*/README.md` - Hướng dẫn từng folder
- `docs/DATABASE_MIGRATION.md` - Quick reference guide

### External Links
- [Liquibase Documentation](https://docs.liquibase.com/)
- [Liquibase SQL Format](https://docs.liquibase.com/concepts/changelogs/sql-format.html)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 🔍 Troubleshooting

### Migration không chạy
```bash
# Check logs
./gradlew :migrated-db:migrate

# Verify database connection
psql -U postgres -d tool
```

### API không start
```bash
# Verify Liquibase disabled in application.yml
spring:
  autoconfigure:
    exclude:
      - org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
```

### Checksum mismatch
```bash
# Don't modify existing changesets!
# Create new changeset to fix issues
```

---

## ✅ Checklist Deploy

- [ ] Build migration JAR: `./gradlew :migrated-db:bootJar`
- [ ] Backup database
- [ ] Run migration: `java -jar tool-migration.jar`
- [ ] Verify migration: Check `databasechangelog` table
- [ ] Build API: `./gradlew :api:bootJar`
- [ ] Deploy API
- [ ] Smoke test

---

**Tác giả:** Senior Developer  
**Ngày tạo:** 2025-12-06  
**Version:** 1.0
