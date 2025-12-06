# Kiến trúc GraphQL + Command Pattern

## Tổng quan luồng xử lý

```
┌─────────────────┐
│  GraphQL Client │
│   (Frontend)    │
└────────┬────────┘
         │
         │ HTTP POST /api/v1/graphql
         │
         ▼
┌─────────────────────────────────────────────────────────┐
│              GraphQL API Layer                          │
│  ┌────────────────────────────────────────────────┐    │
│  │          UserGraphQLApi.java                   │    │
│  │                                                 │    │
│  │  @GraphQLQuery getUser(id)                     │    │
│  │  @GraphQLMutation createUser(input)            │    │
│  │  @GraphQLMutation updateUser(id, input)        │    │
│  │  @GraphQLMutation deleteUser(id)               │    │
│  └────────────────────────────────────────────────┘    │
└───────────┬──────────────────────┬──────────────────────┘
            │                      │
            │ Query                │ Mutation
            │ (đọc)               │ (ghi)
            │                      │
            ▼                      ▼
    ┌──────────────┐      ┌────────────────────┐
    │  Repository  │      │   Command Layer    │
    │   (Direct)   │      │                    │
    └──────────────┘      │  ┌──────────────┐  │
            │             │  │ CreateCommand│  │
            │             │  │ UpdateCommand│  │
            │             │  │ DeleteCommand│  │
            │             │  └──────────────┘  │
            │             └─────────┬──────────┘
            │                       │
            └───────────┬───────────┘
                        │
                        ▼
            ┌─────────────────────┐
            │    JPA Repository   │
            │  (UserRepository)   │
            └──────────┬──────────┘
                       │
                       ▼
            ┌─────────────────────┐
            │      Database       │
            │    (PostgreSQL)     │
            └─────────────────────┘
```

## Chi tiết luồng CREATE

```
Client Request:
mutation {
  createUser(input: { username: "john", email: "john@example.com" })
}
                    ↓
┌─────────────────────────────────────────────────────────┐
│ UserGraphQLApi.createUser(UserCreateInput)              │
│   ↓                                                      │
│   return userCreateCommand.execute(input)               │
└───────────────────────────┬─────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│ UserCreateCommand extends BaseCrudCreateCommandV2       │
│                                                          │
│  1. beforeCreate(input)                                 │
│     └─→ Validation hooks                               │
│                                                          │
│  2. mapInputToEntity(input)                             │
│     ├─→ new User()                                      │
│     └─→ entityMapper.map(input → entity)               │
│         ├─ username: "john"                             │
│         └─ email: "john@example.com"                    │
│                                                          │
│  3. beforeSave(entity)                                  │
│     └─→ Set defaults, custom logic                     │
│                                                          │
│  4. repository.save(entity)                             │
│     └─→ INSERT INTO users ...                          │
│                                                          │
│  5. afterSave(savedEntity)                              │
│     └─→ Send events, clear cache                       │
│                                                          │
│  6. extractId(savedEntity)                              │
│     └─→ return entity.getId()                          │
│                                                          │
└───────────────────────────┬─────────────────────────────┘
                            ↓
                    Return: UUID
                            ↓
Client Response:
{
  "data": {
    "createUser": "123e4567-e89b-12d3-a456-426614174000"
  }
}
```

## Chi tiết luồng UPDATE

```
Client Request:
mutation {
  updateUser(
    id: "123e4567-...",
    input: { email: "newemail@example.com" }
  )
}
                    ↓
┌─────────────────────────────────────────────────────────┐
│ UserGraphQLApi.updateUser(id, UserUpdateInput)          │
│   ↓                                                      │
│   input.setId(UUID.fromString(id))                      │
│   return userUpdateCommand.execute(input)               │
└───────────────────────────┬─────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│ UserUpdateCommand extends BaseCrudUpdateCommandV2       │
│                                                          │
│  1. extractId(input)                                    │
│     └─→ id = input.getId()                             │
│                                                          │
│  2. repository.findById(id)                             │
│     └─→ SELECT * FROM users WHERE id = ?               │
│     ├─ Existing: { username: "john",                    │
│     │              email: "john@old.com" }              │
│     └─→ existingEntity                                 │
│                                                          │
│  3. beforeUpdate(input, existingEntity)                 │
│     └─→ Validation hooks                               │
│                                                          │
│  4. updateEntity(existingEntity, input)                 │
│     └─→ entityMapper.map(input → existingEntity)       │
│         ├─ username: "john" (unchanged)                 │
│         └─ email: "newemail@example.com" (UPDATED)      │
│         Note: chỉ update field NON-NULL từ input!       │
│                                                          │
│  5. beforeSave(entity)                                  │
│     └─→ Custom logic before save                       │
│                                                          │
│  6. repository.save(entity)                             │
│     └─→ UPDATE users SET email = ? WHERE id = ?        │
│                                                          │
│  7. afterSave(savedEntity)                              │
│     └─→ Send events, invalidate cache                  │
│                                                          │
│  8. return savedEntity                                  │
│                                                          │
└───────────────────────────┬─────────────────────────────┘
                            ↓
                    Return: User entity
                            ↓
Client Response:
{
  "data": {
    "updateUser": {
      "id": "123e4567-...",
      "username": "john",
      "email": "newemail@example.com"
    }
  }
}
```

## Chi tiết luồng DELETE

```
Client Request:
mutation {
  deleteUser(id: "123e4567-...")
}
                    ↓
┌─────────────────────────────────────────────────────────┐
│ UserGraphQLApi.deleteUser(id)                           │
│   ↓                                                      │
│   userDeleteCommand.execute(UUID.fromString(id))        │
│   return true                                           │
└───────────────────────────┬─────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────┐
│ UserDeleteCommand extends BaseCrudDeleteCommand         │
│                                                          │
│  1. repository.findById(id)                             │
│     └─→ SELECT * FROM users WHERE id = ?               │
│     └─→ existingEntity                                 │
│                                                          │
│  2. beforeDelete(id, entity)                            │
│     └─→ Validation (e.g., can't delete admin)          │
│                                                          │
│  3. performDelete(id, entity)                           │
│     ├─→ Hard delete: repository.delete(entity)         │
│     │   └─→ DELETE FROM users WHERE id = ?             │
│     │                                                    │
│     └─→ Soft delete (if overridden):                   │
│         entity.setActive(false)                         │
│         repository.save(entity)                         │
│         └─→ UPDATE users SET active = false WHERE id=?  │
│                                                          │
│  4. afterDelete(id, entity)                             │
│     └─→ Cleanup: invalidate cache, send event          │
│                                                          │
│  5. return void                                         │
│                                                          │
└─────────────────────────────────────────────────────────┘
                            ↓
Client Response:
{
  "data": {
    "deleteUser": true
  }
}
```

## Chi tiết luồng QUERY (Không dùng Command)

```
Client Request:
query {
  getUser(id: "123e4567-...") {
    id
    username
    email
  }
}
                    ↓
┌─────────────────────────────────────────────────────────┐
│ UserGraphQLApi.getUser(id)                              │
│   ↓                                                      │
│   return userRepository.findById(UUID.fromString(id))   │
│          .orElseThrow(...)                              │
└───────────────────────────┬─────────────────────────────┘
                            ↓
            ┌───────────────────────────┐
            │   UserRepository          │
            │   (JpaRepository)         │
            └─────────┬─────────────────┘
                      ↓
            SELECT * FROM users WHERE id = ?
                      ↓
            Database returns User entity
                      ↓
Client Response:
{
  "data": {
    "getUser": {
      "id": "123e4567-...",
      "username": "john",
      "email": "john@example.com"
    }
  }
}
```

## So sánh: Query vs Mutation

| Khía cạnh          | Query (getUser)       | Mutation (createUser)       |
|--------------------|----------------------|----------------------------|
| **Mục đích**       | Đọc dữ liệu          | Thay đổi dữ liệu           |
| **Luồng**          | API → Repository     | API → Command → Repository |
| **Validation**     | Minimal              | Full (trong Command)       |
| **Business Logic** | Không có             | Có (hooks trong Command)   |
| **Caching**        | Có thể cache         | Không cache                |
| **Side Effects**   | Không có             | Có (events, notifications) |
| **Return Type**    | Entity               | ID hoặc Entity             |

## Module Dependencies

```
┌─────────────────────────────────────────────┐
│             tools/0-api                     │
│  - UserGraphQLApi.java                      │
│  - REST Controllers (if any)                │
└──────────────┬──────────────────────────────┘
               │ depends on
               ↓
┌─────────────────────────────────────────────┐
│            tools/1-core                      │
│  - UserCreateCommand.java                   │
│  - UserUpdateCommand.java                   │
│  - UserDeleteCommand.java                   │
│  - UserCreateInput.java                     │
│  - UserUpdateInput.java                     │
└──────────────┬──────────────────────────────┘
               │ depends on
               ↓
┌─────────────────────────────────────────────┐
│           tools/2-domain                     │
│  - User.java (Entity)                       │
│  - UserRepository.java                      │
└──────────────┬──────────────────────────────┘
               │ depends on
               ↓
┌─────────────────────────────────────────────┐
│           common/1-core                      │
│  - BaseCrudCreateCommandV2                  │
│  - BaseCrudUpdateCommandV2                  │
│  - BaseCrudDeleteCommand                    │
│  - EntityMapper                             │
│  - Command interface                        │
└─────────────────────────────────────────────┘
```

## File Structure

```
BE/
├── common/
│   └── 1-core/
│       └── src/main/java/com/ttl/common/core/
│           ├── command/
│           │   ├── Command.java
│           │   ├── BaseCommand.java
│           │   ├── TransactionalCommand.java
│           │   └── crud/
│           │       ├── BaseCrudCreateCommandV2.java
│           │       ├── BaseCrudUpdateCommandV2.java
│           │       └── BaseCrudDeleteCommand.java
│           └── mapper/
│               └── EntityMapper.java
│
└── tools/
    ├── 0-api/
    │   └── src/main/java/com/ttl/tool/api/
    │       └── graphql/
    │           └── UserGraphQLApi.java          ← GraphQL API
    │
    ├── 1-core/
    │   └── src/main/java/com/ttl/tool/core/
    │       ├── command/
    │       │   └── user/
    │       │       ├── UserCreateCommand.java   ← Commands
    │       │       ├── UserUpdateCommand.java
    │       │       └── UserDeleteCommand.java
    │       └── dto/
    │           └── input/
    │               ├── UserCreateInput.java     ← Input DTOs
    │               └── UserUpdateInput.java
    │
    └── 2-domain/
        └── src/main/java/com/ttl/tool/domain/
            ├── entity/
            │   └── User.java                    ← Entity
            └── repository/
                └── UserRepository.java          ← Repository
```

## Lợi ích của kiến trúc này

### 1. **Separation of Concerns**
- GraphQL API: Chỉ lo routing và input/output
- Command: Business logic và validation
- Repository: Database access
- Entity: Data model

### 2. **Reusability**
Commands có thể được dùng từ nhiều nơi:
```
GraphQL API  ──┐
REST API     ──┼──→ UserCreateCommand ──→ Repository
Scheduler    ──┘
```

### 3. **Testability**
```java
// Test Command độc lập
@Test
void testCreateUser() {
    UserCreateInput input = new UserCreateInput("john", "john@example.com");
    UUID userId = userCreateCommand.execute(input);
    assertNotNull(userId);
}

// Test GraphQL API độc lập
@Test
void testGraphQLCreateUser() {
    // Mock command
    when(userCreateCommand.execute(any())).thenReturn(mockUUID);
    // Test GraphQL layer
}
```

### 4. **Extensibility**
Dễ dàng mở rộng với hooks:
```java
@Override
protected void afterSave(User user, UserCreateInput input, CommandHolder holder) {
    // Send welcome email
    emailService.sendWelcomeEmail(user);
    
    // Publish event
    eventPublisher.publish(new UserCreatedEvent(user));
    
    // Clear cache
    cacheManager.evict("users");
}
```

### 5. **Consistency**
Mọi cách tạo User đều đi qua cùng một Command:
- Đảm bảo validation nhất quán
- Business logic được thực thi đồng nhất
- Dễ audit và debug
