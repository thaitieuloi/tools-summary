# Command Pattern CRUD Framework

## Tổng quan

Hệ thống Command Pattern CRUD được thiết kế để giảm thiểu boilerplate code khi implement các API CRUD. Thay vì phải viết đi viết lại logic tương tự cho mỗi entity, bạn chỉ cần extend các base command classes và framework sẽ tự động xử lý mapping, validation, và persistence.

## Kiến trúc

### 1. Core Components

```
common/1-core/
├── command/
│   ├── Command.java              # Base interface
│   ├── BaseCommand.java          # Abstract base với lifecycle hooks
│   ├── TransactionalCommand.java # Transactional marker
│   ├── CommandHolder.java        # Input + Context wrapper
│   ├── Context.java              # Execution context
│   └── crud/
│       ├── BaseCrudCreateCommand.java      # Generic CREATE (manual mapping)
│       ├── BaseCrudCreateCommandV2.java    # CREATE với auto-mapping ⭐
│       ├── BaseCrudUpdateCommand.java      # Generic UPDATE (manual mapping)
│       ├── BaseCrudUpdateCommandV2.java    # UPDATE với auto-mapping ⭐
│       └── BaseCrudDeleteCommand.java      # Generic DELETE
└── mapper/
    └── EntityMapper.java         # Auto-mapping utility
```

### 2. Implementation Pattern

```
tools/1-core/
├── dto/
│   └── input/
│       ├── UserCreateInput.java
│       └── UserUpdateInput.java
└── command/
    └── user/
        ├── UserCreateCommand.java
        ├── UserUpdateCommand.java
        └── UserDeleteCommand.java
```

## Cách sử dụng

### 1. CREATE Command - Cách đơn giản nhất

```java
@Service
public class UserCreateCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    public UserCreateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
    
    // Chỉ cần vậy! Auto-mapping sẽ xử lý mọi thứ.
}
```

**Giải thích:**
- `User`: Entity type
- `UserCreateInput`: Input DTO type
- `UUID`: ID type của entity

Framework sẽ tự động:
1. Tạo instance mới của `User`
2. Copy tất cả fields matching từ `UserCreateInput` sang `User`
3. Lưu vào database via `repository.save()`
4. Trả về ID của entity vừa tạo

### 2. CREATE Command - Với custom logic

```java
@Service
public class UserCreateCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    private final PasswordEncoder passwordEncoder;
    
    public UserCreateCommand(UserRepository repository, 
                            EntityMapper mapper,
                            PasswordEncoder passwordEncoder) {
        super(repository, mapper);
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    protected User beforeSave(User entity, UserCreateInput input, 
                             CommandHolder<UserCreateInput> holder) {
        // Thêm logic custom: encode password
        if (input.getPassword() != null) {
            entity.setPassword(passwordEncoder.encode(input.getPassword()));
        }
        
        // Set default values
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
        
        return entity;
    }
    
    @Override
    protected void afterSave(User savedEntity, UserCreateInput input,
                            CommandHolder<UserCreateInput> holder) {
        log.info("User created: {}", savedEntity.getId());
        // Publish event, send email, etc.
    }
}
```

### 3. UPDATE Command

```java
@Service
public class UserUpdateCommand 
    extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {
    
    public UserUpdateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
    
    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();  // Chỉ cần implement method này
    }
    
    // Auto-mapping sẽ update các fields non-null từ input vào entity
}
```

**Lifecycle:**
1. Extract ID từ input
2. Find entity by ID (throw exception nếu không tìm thấy)
3. Auto-map fields từ input sang entity (chỉ fields non-null)
4. Save entity
5. Return updated entity

### 4. UPDATE Command - Với validation

```java
@Service
public class UserUpdateCommand 
    extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {
    
    private final UserRepository repository;
    
    public UserUpdateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }
    
    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();
    }
    
    @Override
    protected UserUpdateInput beforeUpdate(UserUpdateInput input, 
                                          User existingEntity,
                                          CommandHolder<UserUpdateInput> holder) {
        // Validation
        if (input.getEmail() != null) {
            // Check email uniqueness
            repository.findByEmail(input.getEmail())
                .filter(u -> !u.getId().equals(input.getId()))
                .ifPresent(u -> {
                    throw new IllegalArgumentException("Email already exists");
                });
        }
        
        return input;
    }
}
```

### 5. DELETE Command

```java
@Service
public class UserDeleteCommand 
    extends BaseCrudDeleteCommand<User, UUID> {
    
    public UserDeleteCommand(UserRepository repository) {
        super(repository);
    }
    
    // Chỉ cần vậy! Hard delete tự động.
}
```

### 6. DELETE Command - Soft Delete

```java
@Service
public class UserDeleteCommand 
    extends BaseCrudDeleteCommand<User, UUID> {
    
    public UserDeleteCommand(UserRepository repository) {
        super(repository);
    }
    
    @Override
    protected void performDelete(UUID id, User entity) {
        // Soft delete thay vì hard delete
        entity.setActive(false);
        repository.save(entity);
    }
    
    @Override
    protected void afterDelete(UUID id, User entity, 
                              CommandHolder<UUID> holder) {
        log.info("User soft-deleted: {}", id);
        // Clear cache, publish event, etc.
    }
}
```

## Lifecycle Hooks

Mỗi command có các hooks bạn có thể override:

### CREATE Command Hooks

```java
beforeCreate(Input input, CommandHolder<Input> holder)
  ↓
mapInputToEntity(Input input, CommandHolder<Input> holder)
  ↓
beforeSave(Entity entity, Input input, CommandHolder<Input> holder)
  ↓
repository.save(entity)
  ↓
afterSave(Entity savedEntity, Input input, CommandHolder<Input> holder)
  ↓
extractId(Entity entity)
```

### UPDATE Command Hooks

```java
extractId(Input input)
  ↓
repository.findById(id)
  ↓
beforeUpdate(Input input, Entity existingEntity, CommandHolder<Input> holder)
  ↓
updateEntity(Entity entity, Input input, CommandHolder<Input> holder)
  ↓
beforeSave(Entity entity, Input input, CommandHolder<Input> holder)
  ↓
repository.save(entity)
  ↓
afterSave(Entity savedEntity, Input input, CommandHolder<Input> holder)
```

### DELETE Command Hooks

```java
findEntity(ID id)
  ↓
beforeDelete(ID id, Entity entity, CommandHolder<ID> holder)
  ↓
performDelete(ID id, Entity entity)
  ↓
afterDelete(ID id, Entity entity, CommandHolder<ID> holder)
```

## Auto-Mapping với EntityMapper

### Excluded Fields

Mặc định, các fields sau sẽ KHÔNG được auto-map:

**CREATE:**
- `id`
- `createdAt`
- `updatedAt`
- `createdBy`
- `updatedBy`

**UPDATE:**
- `id`
- `createdAt`
- `createdBy`

### Custom Excluded Fields

```java
@Override
protected Set<String> getExcludedFields() {
    var defaultExcluded = super.getExcludedFields();
    var custom = new HashSet<>(defaultExcluded);
    custom.add("password");  // Thêm field custom
    return custom;
}
```

## Sử dụng trong Controller/API

### GraphQL API Example

```java
@GraphQLApi
@RequiredArgsConstructor
public class UserApi {
    
    private final UserCreateCommand createCommand;
    private final UserUpdateCommand updateCommand;
    private final UserDeleteCommand deleteCommand;
    
    @GraphQLMutation
    public UUID createUser(@GraphQLArgument UserCreateInput input) {
        return createCommand.execute(input);
    }
    
    @GraphQLMutation
    public User updateUser(@GraphQLArgument UserUpdateInput input) {
        return updateCommand.execute(input);
    }
    
    @GraphQLMutation
    public Boolean deleteUser(@GraphQLArgument UUID id) {
        deleteCommand.execute(id);
        return true;
    }
}
```

### REST API Example

```java
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserCreateCommand createCommand;
    private final UserUpdateCommand updateCommand;
    private final UserDeleteCommand deleteCommand;
    
    @PostMapping
    public ResponseEntity<UUID> create(@RequestBody UserCreateInput input) {
        UUID id = createCommand.execute(input);
        return ResponseEntity.ok(id);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id,
                                      @RequestBody UserUpdateInput input) {
        input.setId(id);
        User user = updateCommand.execute(input);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCommand.execute(id);
        return ResponseEntity.noContent().build();
    }
}
```

## So sánh với code cũ

### Code cũ (nhiều boilerplate)

```java
@Service
public class UserCreateCommand {
    private final UserRepository repository;
    private final BeanMapper mapper;
    
    public String execute(UserCreateInput input) {
        // Manual validation
        if (input.getUsername() == null) {
            throw new ValidationException("Username required");
        }
        
        // Manual mapping
        UserEntity user = new UserEntity();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setAvatar(input.getAvatar());
        // ... 10+ more fields
        
        // Save
        user = repository.save(user);
        
        return user.getId();
    }
}
```

### Code mới (minimal boilerplate)

```java
@Service
public class UserCreateCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    public UserCreateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
    
    // Done! Auto-mapping handles everything.
}
```

## Ưu điểm

✅ **Giảm 80-90% boilerplate code**
✅ **Consistent pattern** cho tất cả CRUD operations
✅ **Auto-mapping** giữa DTO và Entity
✅ **Flexible hooks** cho custom logic
✅ **Transaction support** built-in
✅ **Easy to test** với clear responsibilities
✅ **Type-safe** với generics

## Best Practices

1. **DTO Design**: Input DTOs nên có field names matching với Entity
2. **Validation**: Sử dụng hooks `beforeCreate` hoặc `beforeUpdate` cho validation
3. **Events**: Sử dụng hooks `afterSave` hoặc `afterDelete` để publish events
4. **Soft Delete**: Override `performDelete` cho soft delete pattern
5. **Logging**: Sử dụng `@Slf4j` và log trong hooks khi cần

## Next Steps

1. Tạo Input DTOs matching với entities
2. Extend appropriate base command class
3. Implement required abstract methods (if any)
4. Override hooks cho custom logic
5. Use command trong API layer

---

**Happy Coding! 🚀**
