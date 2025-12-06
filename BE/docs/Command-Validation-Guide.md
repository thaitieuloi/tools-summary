# Validation trong Command Pattern với YAVI

## Tổng quan

Validation được tích hợp sẵn vào `BaseCommand` sử dụng **YAVI** (Yet Another Validation) library - một fluent validation library cho Java.

### Luồng validation

```
GraphQL API
    ↓
Command.execute(input)
    ↓
validate(input)              ← Tự động validate
    ├─ getValidatorBuilder()  ← Override để định nghĩa rules
    ├─ build validator
    ├─ validate input
    └─ throw ValidationException nếu có lỗi
    ↓
onExecute(input)             ← Chỉ execute nếu validation pass
```

## Setup

### 1. Dependency đã được thêm

```gradle
// common/1-core/build.gradle
dependencies {
    api 'am.ik.yavi:yavi:0.14.1'
}
```

### 2. BaseCommand đã hỗ trợ validation

`BaseCommand` tự động gọi `validate()` trước khi execute. Bạn chỉ cần override `getValidatorBuilder()` để định nghĩa validation rules.

## Ví dụ cơ bản

### UserCreateCommand với validation

```java
@Service
public class UserCreateCommand extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {

    private final UserRepository userRepository;

    public UserCreateCommand(UserRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
        this.userRepository = repository;
    }

    /**
     * Define validation rules using YAVI
     */
    @Override
    protected ValidatorBuilder<UserCreateInput> getValidatorBuilder(CommandHolder<UserCreateInput> holder) {
        return ValidatorBuilder.<UserCreateInput>of()
            // Username validation
            .constraint(UserCreateInput::getUsername, "username", c -> c
                .notBlank().message("Username is required")
                .lessThanOrEqual(50).message("Username must not exceed 50 characters")
            )
            // Email validation
            .constraint(UserCreateInput::getEmail, "email", c -> c
                .notBlank().message("Email is required")
                .email().message("Email must be a valid email address")
            )
            // Custom constraint: Check username uniqueness
            .constraintOnTarget(input -> 
                !userRepository.existsByUsername(input.getUsername()),
                "username",
                "c.username.unique",
                "Username already exists"
            );
    }
}
```

### Các loại validation

#### 1. **Field-level validation** (trên từng field)

```java
.constraint(UserCreateInput::getUsername, "username", c -> c
    .notBlank()                    // Không được rỗng
    .notNull()                      // Không được null
    .lessThanOrEqual(50)            // Độ dài <= 50
    .greaterThanOrEqual(3)          // Độ dài >= 3
    .pattern("[a-zA-Z0-9_]+")       // Regex pattern
)
```

#### 2. **Email validation**

```java
.constraint(UserCreateInput::getEmail, "email", c -> c
    .notBlank()
    .email()                        // Validate email format
)
```

#### 3. **Number validation**

```java
.constraint(ProductCreateInput::getPrice, "price", c -> c
    .notNull()
    .greaterThan(0)                 // > 0
    .lessThanOrEqual(1000000)       // <= 1,000,000
)
```

#### 4. **Custom constraint trên object**

```java
// Validate toàn bộ input object
.constraintOnTarget(input -> {
    // Custom logic
    return input.getPassword().equals(input.getConfirmPassword());
}, "confirmPassword", "error.password.mismatch", "Passwords do not match")
```

#### 5. **Nested object validation**

```java
// Validate object bên trong
.nest(UserCreateInput::getAddress, "address", 
    ValidatorBuilder.<AddressInput>of()
        .constraint(AddressInput::getCity, "city", c -> c.notBlank())
        .constraint(AddressInput::getZipCode, "zipCode", c -> c.notBlank())
        .build()
)
```

#### 6. **Collection validation**

```java
.forEach(ProductInput::getTags, "tags",
    ValidatorBuilder.<String>of()
        .constraint(tag -> tag, "tag", c -> c.notBlank().lessThanOrEqual(20))
        .build()
)
```

## Ví dụ đầy đủ

### ProductCreateCommand

```java
@Service
public class ProductCreateCommand extends BaseCrudCreateCommandV2<Product, ProductCreateInput, UUID> {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    protected ValidatorBuilder<ProductCreateInput> getValidatorBuilder(CommandHolder<ProductCreateInput> holder) {
        return ValidatorBuilder.<ProductCreateInput>of()
            
            // Product name
            .constraint(ProductCreateInput::getName, "name", c -> c
                .notBlank().message("Product name is required")
                .lessThanOrEqual(100).message("Product name must not exceed 100 characters")
            )
            
            // Description (optional)
            .constraint(ProductCreateInput::getDescription, "description", c -> c
                .lessThanOrEqual(500).message("Description must not exceed 500 characters")
            )
            
            // Price
            .constraint(ProductCreateInput::getPrice, "price", c -> c
                .notNull().message("Price is required")
                .greaterThan(BigDecimal.ZERO).message("Price must be greater than 0")
                .lessThanOrEqual(new BigDecimal("999999999")).message("Price is too large")
            )
            
            // Stock quantity
            .constraint(ProductCreateInput::getStock, "stock", c -> c
                .notNull()
                .greaterThanOrEqual(0).message("Stock cannot be negative")
            )
            
            // SKU (Stock Keeping Unit) - unique code
            .constraint(ProductCreateInput::getSku, "sku", c -> c
                .notBlank()
                .pattern("^[A-Z0-9-]+$").message("SKU must contain only uppercase letters, numbers and hyphens")
            )
            
            // Custom: Check SKU uniqueness
            .constraintOnTarget(input ->
                !productRepository.existsBySku(input.getSku()),
                "sku",
                "error.sku.exists",
                "SKU already exists"
            )
            
            // Custom: Check category exists
            .constraintOnTarget(input ->
                categoryRepository.existsById(input.getCategoryId()),
                "categoryId",
                "error.category.notFound",
                "Category does not exist"
            )
            
            // Complex validation: Discount logic
            .constraintOnTarget(input -> {
                if (input.getDiscountPercent() != null) {
                    return input.getDiscountPercent() >= 0 && input.getDiscountPercent() <= 100;
                }
                return true;
            }, "discountPercent", "error.discount.invalid", "Discount must be between 0 and 100");
    }

    @Override
    protected Product beforeSave(Product entity, ProductCreateInput input, CommandHolder<ProductCreateInput> holder) {
        // Set defaults
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        
        // Calculate final price if discount exists
        if (input.getDiscountPercent() != null && input.getDiscountPercent() > 0) {
            BigDecimal discount = entity.getPrice()
                .multiply(BigDecimal.valueOf(input.getDiscountPercent()))
                .divide(BigDecimal.valueOf(100));
            entity.setFinalPrice(entity.getPrice().subtract(discount));
        } else {
            entity.setFinalPrice(entity.getPrice());
        }
        
        return entity;
    }
}
```

## Xử lý lỗi validation

### 1. Trong GraphQL API

```java
@GraphQLMutation(name = "createUser")
public UUID createUser(@GraphQLArgument(name = "input") UserCreateInput input) {
    try {
        return userCreateCommand.execute(input);
    } catch (ValidationException ex) {
        // GraphQL sẽ tự động convert exception thành error response
        // Client sẽ nhận được error message chi tiết
        throw ex;
    }
}
```

### 2. Response khi validation fail

GraphQL response khi validation fail:

```json
{
  "errors": [
    {
      "message": "Validation failed: username: Username is required, email: Email must be a valid email address",
      "extensions": {
        "classification": "ValidationException"
      }
    }
  ],
  "data": {
    "createUser": null
  }
}
```

### 3. Custom error handler

Nếu muốn format error theo ý bạn, tạo GraphQL error handler:

```java
@Component
public class GraphQLExceptionHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        return errors.stream()
            .map(this::transformError)
            .collect(Collectors.toList());
    }

    private GraphQLError transformError(GraphQLError error) {
        if (error instanceof ExceptionWhileDataFetching) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            if (exceptionError.getException() instanceof ValidationException) {
                ValidationException validationEx = (ValidationException) exceptionError.getException();
                
                // Extract violations
                Map<String, List<String>> fieldErrors = new HashMap<>();
                validationEx.getViolations().forEach(v -> {
                    fieldErrors.computeIfAbsent(v.name(), k -> new ArrayList<>()).add(v.message());
                });

                return GraphQLError.newError()
                    .message("Validation failed")
                    .extensions(Map.of(
                        "code", "VALIDATION_ERROR",
                        "fields", fieldErrors
                    ))
                    .build();
            }
        }
        return error;
    }
}
```

Response với custom error handler:

```json
{
  "errors": [
    {
      "message": "Validation failed",
      "extensions": {
        "code": "VALIDATION_ERROR",
        "fields": {
          "username": ["Username is required", "Username must not exceed 50 characters"],
          "email": ["Email must be a valid email address"]
        }
      }
    }
  ]
}
```

## Validation patterns

### 1. Password validation

```java
.constraint(UserCreateInput::getPassword, "password", c -> c
    .notBlank()
    .greaterThanOrEqual(8).message("Password must be at least 8 characters")
    .lessThanOrEqual(100)
    .pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$")
        .message("Password must contain uppercase, lowercase, number and special character")
)
.constraintOnTarget(input ->
    input.getPassword().equals(input.getConfirmPassword()),
    "confirmPassword",
    "error.password.mismatch",
    "Passwords do not match"
)
```

### 2. Phone number validation

```java
.constraint(UserCreateInput::getPhone, "phone", c -> c
    .pattern("^\\+?[1-9]\\d{1,14}$").message("Invalid phone number format")
)
```

### 3. Date range validation

```java
.constraintOnTarget(input -> {
    if (input.getStartDate() != null && input.getEndDate() != null) {
        return input.getEndDate().isAfter(input.getStartDate());
    }
    return true;
}, "endDate", "error.date.range", "End date must be after start date")
```

### 4. Conditional validation

```java
.constraintOnTarget(input -> {
    // Only validate shipping address if shipping method is selected
    if (input.getShippingMethod() != null) {
        return input.getShippingAddress() != null;
    }
    return true;
}, "shippingAddress", "error.shipping.required", "Shipping address is required")
```

### 5. Dynamic validation dựa trên context

```java
@Override
protected ValidatorBuilder<ProductUpdateInput> getValidatorBuilder(CommandHolder<ProductUpdateInput> holder) {
    Context context = holder.getContext();
    String userRole = context.get("userRole", String.class);

    ValidatorBuilder<ProductUpdateInput> builder = ValidatorBuilder.<ProductUpdateInput>of()
        .constraint(ProductUpdateInput::getName, "name", c -> c.notBlank());

    // Admin có thể set price bất kỳ, user thường không thể > 1000
    if (!"ADMIN".equals(userRole)) {
        builder.constraint(ProductUpdateInput::getPrice, "price", c -> c
            .lessThanOrEqual(new BigDecimal("1000"))
            .message("Regular users cannot set price above 1000")
        );
    }

    return builder;
}
```

## Best Practices

### 1. **Tách validation phức tạp**

```java
// Tạo utility method cho validation logic phức tạp
private boolean isValidDiscountCombination(ProductInput input) {
    if (input.getDiscountPercent() != null && input.getDiscountAmount() != null) {
        return false; // Không cho phép cả discount % và discount amount
    }
    return true;
}

@Override
protected ValidatorBuilder<ProductInput> getValidatorBuilder(CommandHolder<ProductInput> holder) {
    return ValidatorBuilder.<ProductInput>of()
        .constraintOnTarget(
            this::isValidDiscountCombination,
            "discount",
            "error.discount.invalid",
            "Cannot have both discount percent and discount amount"
        );
}
```

### 2. **Reuse validators**

```java
// Tạo validator constants
private static final ValidatorBuilder<String> EMAIL_VALIDATOR = 
    ValidatorBuilder.<String>of()
        .constraint(email -> email, "email", c -> c.notBlank().email());

private static final ValidatorBuilder<String> PHONE_VALIDATOR =
    ValidatorBuilder.<String>of()
        .constraint(phone -> phone, "phone", c -> c.pattern("^\\+?[1-9]\\d{1,14}$"));
```

### 3. **Clear error messages**

```java
// ❌ Bad: Vague message
.notBlank().message("Invalid")

// ✅ Good: Specific message
.notBlank().message("Username is required and cannot be empty")
```

### 4. **Validation vs Business Logic**

- **Validation**: Input format, constraints, data integrity
- **Business Logic**: Domain rules, state transitions

```java
// ✅ Validation trong getValidatorBuilder()
.constraint(UserInput::getAge, "age", c -> c
    .greaterThanOrEqual(0)
    .lessThanOrEqual(150)
)

// ✅ Business logic trong beforeSave()
@Override
protected User beforeSave(User entity, UserInput input, CommandHolder holder) {
    // Business rule: Users under 18 cannot be admins
    if (entity.getAge() < 18 && entity.getRole() == Role.ADMIN) {
        throw new BusinessException("Users under 18 cannot be assigned admin role");
    }
    return entity;
}
```

## Testing

```java
@SpringBootTest
class UserCreateCommandTest {

    @Autowired
    private UserCreateCommand userCreateCommand;

    @Test
    void shouldFailValidationWhenUsernameIsBlank() {
        UserCreateInput input = UserCreateInput.builder()
            .username("")  // Invalid: blank
            .email("test@example.com")
            .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            userCreateCommand.execute(input);
        });

        assertTrue(ex.getMessage().contains("Username is required"));
    }

    @Test
    void shouldFailValidationWhenEmailIsInvalid() {
        UserCreateInput input = UserCreateInput.builder()
            .username("john_doe")
            .email("invalid-email")  // Invalid format
            .build();

        ValidationException ex = assertThrows(ValidationException.class, () -> {
            userCreateCommand.execute(input);
        });

        assertTrue(ex.getMessage().contains("valid email address"));
    }

    @Test
    void shouldCreateUserWithValidInput() {
        UserCreateInput input = UserCreateInput.builder()
            .username("john_doe")
            .email("john@example.com")
            .build();

        UUID userId = userCreateCommand.execute(input);
        assertNotNull(userId);
    }
}
```

## Tổng kết

✅ **Validation tự động** - Chỉ cần override `getValidatorBuilder()`  
✅ **Fluent API** - YAVI cung cấp syntax dễ đọc, dễ maintain  
✅ **Type-safe** - Compile-time checking  
✅ **Flexible** - Hỗ trợ field-level, object-level, nested, conditional validation  
✅ **Clear errors** - Error messages rõ ràng, dễ hiểu cho client  

**Validation là bước quan trọng để đảm bảo data integrity trước khi thực thi business logic!**
