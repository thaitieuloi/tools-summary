# 05-trigger - Database Triggers

This folder contains SQL files for creating database triggers.

## 📌 Important: Use SQL Files

For triggers, **always use `.sql` files** because:
- ✅ Clear syntax
- ✅ Easy to test
- ✅ Better readability
- ✅ Can reference functions

## 📝 SQL File Format

```sql
-- liquibase formatted sql
-- changeset author:changeset-id dbms:postgresql
-- comment: Description

CREATE TRIGGER trigger_name
BEFORE|AFTER INSERT|UPDATE|DELETE ON table_name
FOR EACH ROW
EXECUTE FUNCTION function_name();

-- rollback DROP TRIGGER IF EXISTS trigger_name ON table_name;
```

## 📚 Examples

### Example 1: Auto-Update Timestamp

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-trigger-001-user-timestamp dbms:postgresql
-- comment: Auto-update user.updated_at on changes

CREATE TRIGGER trigger_update_user_timestamp
BEFORE UPDATE ON user
FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- rollback DROP TRIGGER IF EXISTS trigger_update_user_timestamp ON user;
```

### Example 2: Audit Log Trigger

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-trigger-002-order-audit dbms:postgresql
-- comment: Log all order changes to audit table

CREATE TRIGGER trigger_order_audit
AFTER INSERT OR UPDATE OR DELETE ON orders
FOR EACH ROW
EXECUTE FUNCTION log_order_changes();

-- rollback DROP TRIGGER IF EXISTS trigger_order_audit ON orders;
```

### Example 3: Conditional Trigger (with WHEN)

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-trigger-003-status-change dbms:postgresql
-- comment: Send notification when order status changes

CREATE TRIGGER trigger_order_status_notification
AFTER UPDATE ON orders
FOR EACH ROW
WHEN (OLD.status IS DISTINCT FROM NEW.status)
EXECUTE FUNCTION notify_order_status_change();

-- rollback DROP TRIGGER IF EXISTS trigger_order_status_notification ON orders;
```

## ⚠️ Best Practices

1. **Always specify dbms**: `dbms:postgresql`
2. **Use DROP IF EXISTS**: In rollback
3. **Name clearly**: `trigger_<table>_<action>`
4. **Add WHEN clause**: For efficiency when possible
5. **Test thoroughly**: Triggers can impact performance

## 📋 Trigger Naming Convention

- `trigger_update_<table>_timestamp` - Auto-update timestamp
- `trigger_<table>_audit` - Audit logging
- `trigger_<table>_validate_<field>` - Validation
- `trigger_<table>_cascade_<action>` - Cascade operations
