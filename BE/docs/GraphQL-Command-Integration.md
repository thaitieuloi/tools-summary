# Kết nối GraphQL API với Command Pattern

## Kiến trúc tổng quan

```
GraphQL API → Command → Repository → Database
```

### Luồng xử lý

1. **GraphQL Query** (đọc dữ liệu): 
   - Gọi trực tiếp `Repository` 
   - Không cần Command vì không có business logic phức tạp

2. **GraphQL Mutation** (thay đổi dữ liệu):
   - Gọi `Command` để thực thi business logic
   - Command tự động xử lý validation, mapping, save

## Ví dụ cụ thể: User CRUD

### 1. GraphQL API Layer (`UserGraphQLApi.java`)

```java
@Component
@GraphQLApi
@RequiredArgsConstructor
public class UserGraphQLApi {

    // Commands for mutations
    private final UserCreateCommand userCreateCommand;
    private final UserUpdateCommand userUpdateCommand;
    private final UserDeleteCommand userDeleteCommand;
    
    // Repository for queries
    private final UserRepository userRepository;

    // QUERY: Gọi trực tiếp repository
    @GraphQLQuery(name = "getUser")
    public User getUser(@GraphQLArgument(name = "id") String id) {
        return userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // MUTATION: Gọi command để xử lý business logic
    @GraphQLMutation(name = "createUser")
    public UUID createUser(@GraphQLArgument(name = "input") UserCreateInput input) {
        return userCreateCommand.execute(input);
    }
    
    @GraphQLMutation(name = "updateUser")
    public User updateUser(
            @GraphQLArgument(name = "id") String id,
            @GraphQLArgument(name = "input") UserUpdateInput input) {
        input.setId(UUID.fromString(id));
        return userUpdateCommand.execute(input);
    }
    
    @GraphQLMutation(name = "deleteUser")
    public Boolean deleteUser(@GraphQLArgument(name = "id") String id) {
        userDeleteCommand.execute(UUID.fromString(id));
        return true;
    }
}
```

### 2. Command Layer

#### UserCreateCommand

```java
@Service
public class UserCreateCommand extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {

    public UserCreateCommand(UserRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
    }
    
    // Tất cả logic đã được handle bởi BaseCrudCreateCommandV2:
    // - Auto-mapping từ Input → Entity
    // - Validation hooks
    // - Save vào database
    // - Return ID của entity mới tạo
}
```

**Luồng xử lý trong Command:**
1. `beforeCreate()` - hook để validate input
2. `mapInputToEntity()` - auto-map fields từ UserCreateInput → User entity
3. `beforeSave()` - hook để xử lý trước khi save
4. `repository.save()` - lưu vào database
5. `afterSave()` - hook để xử lý sau khi save (ví dụ: send event)
6. `extractId()` - lấy ID và return

#### UserUpdateCommand

```java
@Service
public class UserUpdateCommand extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {

    public UserUpdateCommand(UserRepository repository, EntityMapper entityMapper) {
        super(repository, entityMapper);
    }

    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();
    }
}
```

**Luồng xử lý:**
1. `extractId()` - lấy ID từ input
2. `repository.findById()` - tìm entity hiện tại
3. `beforeUpdate()` - hook để validate
4. `updateEntity()` - auto-map chỉ những field NON-NULL từ input → entity
5. `beforeSave()` - hook trước khi save
6. `repository.save()` - update vào database
7. `afterSave()` - hook sau khi save
8. Return entity đã update

#### UserDeleteCommand

```java
@Service
public class UserDeleteCommand extends BaseCrudDeleteCommand<User, UUID> {

    public UserDeleteCommand(UserRepository repository) {
        super(repository);
    }
}
```

**Luồng xử lý:**
1. `repository.findById()` - tìm entity
2. `beforeDelete()` - hook để validate (ví dụ: không cho xóa admin)
3. `performDelete()` - thực hiện xóa (có thể override để soft delete)
4. `afterDelete()` - hook để cleanup (cache, send event, etc.)

## Input DTOs

### UserCreateInput.java
```java
@Data
@Builder
public class UserCreateInput {
    private String username;
    private String email;
    private Boolean active;
}
```

### UserUpdateInput.java
```java
@Data
@Builder
public class UserUpdateInput {
    private UUID id;           // Required for update
    private String username;   // Optional - chỉ update nếu không null
    private String email;      // Optional
    private Boolean active;    // Optional
}
```

## GraphQL Queries/Mutations

### Sử dụng GraphQL

#### Query - Lấy user
```graphql
query {
  getUser(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    username
    email
    active
  }
}
```

#### Mutation - Tạo user mới
```graphql
mutation {
  createUser(input: {
    username: "john_doe"
    email: "john@example.com"
    active: true
  })
}
# Returns: UUID của user vừa tạo
```

#### Mutation - Update user
```graphql
mutation {
  updateUser(
    id: "123e4567-e89b-12d3-a456-426614174000"
    input: {
      email: "newemail@example.com"
    }
  ) {
    id
    username
    email
    active
  }
}
# Returns: User entity đã update
```

#### Mutation - Xóa user
```graphql
mutation {
  deleteUser(id: "123e4567-e89b-12d3-a456-426614174000")
}
# Returns: true nếu thành công
```

## Lợi ích của cách tiếp cận này

1. **Tách biệt concerns**:
   - GraphQL API chỉ lo routing và parsing input
   - Command lo business logic
   - Repository lo database access

2. **Reusable Commands**:
   - Commands có thể được gọi từ nhiều nơi (GraphQL, REST, Scheduler, etc.)
   - Không phụ thuộc vào GraphQL

3. **Testable**:
   - Test Commands độc lập với GraphQL
   - Test GraphQL API độc lập với business logic

4. **Maintainable**:
   - Business logic tập trung ở Commands
   - Dễ mở rộng với hooks (beforeCreate, afterSave, etc.)

5. **Auto-mapping**:
   - Tự động map fields từ Input DTO → Entity
   - Giảm thiểu boilerplate code

## Tạo CRUD mới cho Entity khác

Ví dụ: Tạo CRUD cho `Product` entity

### 1. Tạo Commands

```java
// ProductCreateCommand.java
@Service
public class ProductCreateCommand 
    extends BaseCrudCreateCommandV2<Product, ProductCreateInput, UUID> {
    
    public ProductCreateCommand(ProductRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
}

// ProductUpdateCommand.java
@Service
public class ProductUpdateCommand 
    extends BaseCrudUpdateCommandV2<Product, ProductUpdateInput, UUID> {
    
    public ProductUpdateCommand(ProductRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
    
    @Override
    protected UUID extractId(ProductUpdateInput input) {
        return input.getId();
    }
}

// ProductDeleteCommand.java
@Service
public class ProductDeleteCommand 
    extends BaseCrudDeleteCommand<Product, UUID> {
    
    public ProductDeleteCommand(ProductRepository repository) {
        super(repository);
    }
}
```

### 2. Tạo GraphQL API

```java
@Component
@GraphQLApi
@RequiredArgsConstructor
public class ProductGraphQLApi {

    private final ProductCreateCommand createCommand;
    private final ProductUpdateCommand updateCommand;
    private final ProductDeleteCommand deleteCommand;
    private final ProductRepository repository;

    @GraphQLQuery(name = "getProduct")
    public Product getProduct(@GraphQLArgument(name = "id") String id) {
        return repository.findById(UUID.fromString(id))
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @GraphQLMutation(name = "createProduct")
    public UUID createProduct(@GraphQLArgument(name = "input") ProductCreateInput input) {
        return createCommand.execute(input);
    }

    @GraphQLMutation(name = "updateProduct")
    public Product updateProduct(
            @GraphQLArgument(name = "id") String id,
            @GraphQLArgument(name = "input") ProductUpdateInput input) {
        input.setId(UUID.fromString(id));
        return updateCommand.execute(input);
    }

    @GraphQLMutation(name = "deleteProduct")
    public Boolean deleteProduct(@GraphQLArgument(name = "id") String id) {
        deleteCommand.execute(UUID.fromString(id));
        return true;
    }
}
```

### 3. Tạo Input DTOs

```java
@Data
@Builder
public class ProductCreateInput {
    private String name;
    private String description;
    private BigDecimal price;
}

@Data
@Builder
public class ProductUpdateInput {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
}
```

**Xong! Chỉ cần 3 file class + 2 input DTOs là có đủ CRUD hoàn chỉnh.**

## Custom Logic Examples

### Ví dụ 1: Validation trong Command

```java
@Service
public class UserCreateCommand extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {

    private final UserRepository userRepository;

    @Override
    protected UserCreateInput beforeCreate(UserCreateInput input, CommandHolder<UserCreateInput> holder) {
        // Validate username uniqueness
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        return input;
    }
    
    @Override
    protected User beforeSave(User entity, UserCreateInput input, CommandHolder<UserCreateInput> holder) {
        // Set default values
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        return entity;
    }
}
```

### Ví dụ 2: Send Event sau khi tạo

```java
@Service
public class UserCreateCommand extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    protected void afterSave(User savedEntity, UserCreateInput input, CommandHolder<UserCreateInput> holder) {
        // Send event
        eventPublisher.publishEvent(new UserCreatedEvent(savedEntity));
        
        // Log
        log.info("User created successfully: {}", savedEntity.getId());
    }
}
```

### Ví dụ 3: Soft Delete

```java
@Service
public class UserDeleteCommand extends BaseCrudDeleteCommand<User, UUID> {

    @Override
    protected void performDelete(UUID id, User entity) {
        // Soft delete instead of hard delete
        entity.setActive(false);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }
}
```

## Tổng kết

**GraphQL API** → gọi **Command** → Command tự động xử lý:
- Validation
- Mapping
- Business logic
- Database operations
- Event publishing

Chỉ cần extend Base Command classes, mọi thứ được xử lý tự động!
