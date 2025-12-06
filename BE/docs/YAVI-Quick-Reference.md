# YAVI Validation - Quick Reference

## Common Validation Rules

### String Constraints

```java
.constraint(Input::getField, "fieldName", c -> c
    .notNull()                              // Not null
    .notBlank()                             // Not null, empty or whitespace
    .notEmpty()                             // Not null or empty
    .lessThanOrEqual(50)                    // Length <= 50
    .greaterThanOrEqual(3)                  // Length >= 3
    .fixedSize(10)                          // Exactly 10 characters
    .pattern("[A-Za-z]+")                   // Regex pattern
    .email()                                // Valid email format
    .url()                                  // Valid URL format
)
```

### Number Constraints (Integer, Long, BigDecimal, etc.)

```java
.constraint(Input::getPrice, "price", c -> c
    .notNull()
    .greaterThan(0)                         // > 0
    .greaterThanOrEqual(0)                  // >= 0
    .lessThan(1000)                         // < 1000
    .lessThanOrEqual(1000)                  // <= 1000
    .positive()                             // > 0
    .positiveOrZero()                       // >= 0
    .negative()                             // < 0
    .negativeOrZero()                       // <= 0
)
```

### Boolean Constraints

```java
.constraint(Input::getActive, "active", c -> c
    .isTrue()                               // Must be true
    .isFalse()                              // Must be false
)
```

### Collection Constraints

```java
.constraint(Input::getTags, "tags", c -> c
    .notEmpty()                             // Not empty collection
    .lessThanOrEqual(10)                    // Size <= 10
    .greaterThanOrEqual(1)                  // Size >= 1
)
```

## Custom Constraints

### Object-level Constraint

```java
.constraintOnTarget(input -> {
    // Your validation logic here
    return input.getPassword().equals(input.getConfirmPassword());
}, "fieldName", "error.code", "Error message")
```

### With Repository Check

```java
.constraintOnTarget(input ->
    !repository.existsByUsername(input.getUsername()),
    "username",
    "error.username.exists",
    "Username already exists"
)
```

## Custom Messages

```java
.constraint(Input::getUsername, "username", c -> c
    .notBlank().message("Username is required")
    .lessThanOrEqual(50).message("Username must not exceed 50 characters")
)
```

## Nested Object Validation

```java
.nest(Input::getAddress, "address",
    ValidatorBuilder.<AddressInput>of()
        .constraint(AddressInput::getCity, "city", c -> c.notBlank())
        .constraint(AddressInput::getZipCode, "zipCode", c -> c.notBlank())
        .build()
)
```

## Collection Item Validation

```java
.forEach(Input::getItems, "items",
    ValidatorBuilder.<ItemInput>of()
        .constraint(ItemInput::getName, "name", c -> c.notBlank())
        .constraint(ItemInput::getQuantity, "quantity", c -> c.greaterThan(0))
        .build()
)
```

## Common Patterns

### Email Validation
```java
.constraint(Input::getEmail, "email", c -> c
    .notBlank().message("Email is required")
    .email().message("Invalid email format")
)
```

### Password Validation
```java
.constraint(Input::getPassword, "password", c -> c
    .notBlank()
    .greaterThanOrEqual(8).message("Password must be at least 8 characters")
    .pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
        .message("Password must contain uppercase, lowercase and number")
)
```

### Phone Validation
```java
.constraint(Input::getPhone, "phone", c -> c
    .pattern("^\\+?[1-9]\\d{1,14}$").message("Invalid phone number")
)
```

### URL Validation
```java
.constraint(Input::getWebsite, "website", c -> c
    .url().message("Invalid URL format")
)
```

### Price Validation
```java
.constraint(Input::getPrice, "price", c -> c
    .notNull().message("Price is required")
    .greaterThan(BigDecimal.ZERO).message("Price must be positive")
)
```

### Date Range Validation
```java
.constraintOnTarget(input ->
    input.getEndDate().isAfter(input.getStartDate()),
    "endDate",
    "error.date.range",
    "End date must be after start date"
)
```

## Full Example

```java
@Override
protected ValidatorBuilder<UserCreateInput> getValidatorBuilder(CommandHolder<UserCreateInput> holder) {
    return ValidatorBuilder.<UserCreateInput>of()
        // Username
        .constraint(UserCreateInput::getUsername, "username", c -> c
            .notBlank().message("Username is required")
            .greaterThanOrEqual(3).message("Username must be at least 3 characters")
            .lessThanOrEqual(50).message("Username must not exceed 50 characters")
            .pattern("[a-zA-Z0-9_]+").message("Username can only contain letters, numbers and underscore")
        )
        // Email
        .constraint(UserCreateInput::getEmail, "email", c -> c
            .notBlank().message("Email is required")
            .email().message("Invalid email format")
        )
        // Age (optional)
        .constraint(UserCreateInput::getAge, "age", c -> c
            .greaterThanOrEqual(0).message("Age cannot be negative")
            .lessThanOrEqual(150).message("Age must be realistic")
        )
        // Check uniqueness
        .constraintOnTarget(input ->
            !userRepository.existsByUsername(input.getUsername()),
            "username",
            "error.username.exists",
            "Username already exists"
        );
}
```

## Error Handling

### Exception Structure
```java
try {
    command.execute(input);
} catch (ValidationException ex) {
    // ex.getMessage() - Full error message
    // ex.getViolations() - Detailed violations
    // ex.getDetailedMessage() - Formatted with all violations
}
```

### Access Violations
```java
catch (ValidationException ex) {
    ex.getViolations().forEach(violation -> {
        String field = violation.name();        // Field name
        String message = violation.message();   // Error message
        Object value = violation.value();       // Invalid value
        System.out.println(field + ": " + message);
    });
}
```

## Tips

1. **Always provide clear messages** - Users need to understand what's wrong
2. **Validate early** - Fail fast with validation before business logic
3. **Use constraintOnTarget for complex rules** - Database checks, cross-field validation
4. **Keep validation separate from business logic** - Validation = data integrity, Business logic = domain rules
5. **Test your validation** - Write unit tests for validation rules
