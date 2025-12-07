# 04-function - Database Functions

This folder contains SQL files for creating database functions and stored procedures.

## 📌 Important: Use SQL Files

For functions, **always use `.sql` files** because:
- ✅ Better syntax highlighting
- ✅ Can test in database client
- ✅ Easier to debug
- ✅ Supports complex logic

## 📝 SQL File Format

```sql
-- liquibase formatted sql
-- changeset author:changeset-id dbms:postgresql
-- comment: Description

CREATE OR REPLACE FUNCTION function_name()
RETURNS return_type AS $$
BEGIN
    -- Function body
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS function_name();
```

## 📚 Examples

### Example 1: Simple Utility Function

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-function-001-update-timestamp dbms:postgresql
-- comment: Auto-update timestamp function

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS update_updated_at_column();
```

### Example 2: Function with Parameters

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-function-002-calculate-total dbms:postgresql
-- comment: Calculate order total with tax

CREATE OR REPLACE FUNCTION calculate_order_total(
    p_order_id bigint,
    p_tax_rate decimal DEFAULT 0.1
)
RETURNS decimal AS $$
DECLARE
    v_subtotal decimal;
    v_total decimal;
BEGIN
    SELECT SUM(quantity * price) INTO v_subtotal
    FROM order_item
    WHERE order_id = p_order_id;
    
    v_total := v_subtotal * (1 + p_tax_rate);
    
    RETURN v_total;
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS calculate_order_total(bigint, decimal);
```

### Example 3: Function Returning Table

```sql
-- liquibase formatted sql
-- changeset admin:v1.0-function-003-active-users dbms:postgresql
-- comment: Get active users with details

CREATE OR REPLACE FUNCTION get_active_users()
RETURNS TABLE (
    user_id bigint,
    username varchar,
    last_login timestamp
) AS $$
BEGIN
    RETURN QUERY
    SELECT id, username, last_login_at
    FROM user
    WHERE status = 'ACTIVE'
    ORDER BY last_login_at DESC;
END;
$$ LANGUAGE plpgsql;

-- rollback DROP FUNCTION IF EXISTS get_active_users();
```

## ⚠️ Best Practices

1. **Always specify dbms**: `dbms:postgresql`
2. **Use CREATE OR REPLACE**: For idempotency
3. **Include proper rollback**: Drop the function
4. **Add comments**: Explain complex logic
5. **Handle NULL**: Check for NULL inputs
