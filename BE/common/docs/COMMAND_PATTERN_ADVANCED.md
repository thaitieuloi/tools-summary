# Advanced CRUD Command Examples

## 1. Create with Unique Constraint Check

```java
@Service
@Slf4j
public class UserCreateCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    private final UserRepository userRepository;
    
    public UserCreateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
        this.userRepository = repository;
    }
    
    @Override
    protected UserCreateInput beforeCreate(UserCreateInput input, 
                                          CommandHolder<UserCreateInput> holder) {
        // Check unique constraints
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new IllegalArgumentException(
                "Username already exists: " + input.getUsername());
        }
        
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException(
                "Email already exists: " + input.getEmail());
        }
        
        return input;
    }
}
```

## 2. Create or Update Pattern (Upsert)

```java
@Service
public class UserUpsertCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    private final UserRepository userRepository;
    
    public UserUpsertCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
        this.userRepository = repository;
    }
    
    @Override
    protected UUID onExecute(CommandHolder<UserCreateInput> holder) {
        UserCreateInput input = holder.getInput();
        
        // Try to find existing user by email
        Optional<User> existing = userRepository.findByEmail(input.getEmail());
        
        if (existing.isPresent()) {
            // Update existing
            User user = existing.get();
            entityMapper.map(input, user, getExcludedFields());
            user = userRepository.save(user);
            return user.getId();
        } else {
            // Create new
            return super.onExecute(holder);
        }
    }
}
```

## 3. Bulk Create Command

```java
@Service
public class UserBulkCreateCommand 
    extends BaseCommand<List<UserCreateInput>, List<UUID>> 
    implements TransactionalCommand<List<UserCreateInput>, List<UUID>> {
    
    private final UserCreateCommand singleCreateCommand;
    
    public UserBulkCreateCommand(UserCreateCommand singleCreateCommand) {
        this.singleCreateCommand = singleCreateCommand;
    }
    
    @Override
    protected List<UUID> onExecute(CommandHolder<List<UserCreateInput>> holder) {
        List<UserCreateInput> inputs = holder.getInput();
        
        return inputs.stream()
                .map(singleCreateCommand::execute)
                .collect(Collectors.toList());
    }
}
```

## 4. Create with Related Entities

```java
@Service
@Slf4j
public class OrderCreateCommand 
    extends BaseCrudCreateCommandV2<Order, OrderCreateInput, UUID> {
    
    private final OrderItemRepository itemRepository;
    private final EntityMapper entityMapper;
    
    public OrderCreateCommand(OrderRepository repository, 
                             OrderItemRepository itemRepository,
                             EntityMapper entityMapper) {
        super(repository, entityMapper);
        this.itemRepository = itemRepository;
    }
    
    @Override
    protected void afterSave(Order savedOrder, OrderCreateInput input,
                            CommandHolder<OrderCreateInput> holder) {
        // Create related entities
        if (input.getItems() != null && !input.getItems().isEmpty()) {
            List<OrderItem> items = input.getItems().stream()
                    .map(itemInput -> {
                        OrderItem item = entityMapper.map(itemInput, OrderItem.class);
                        item.setOrder(savedOrder);
                        return item;
                    })
                    .collect(Collectors.toList());
            
            itemRepository.saveAll(items);
            log.info("Created {} items for order {}", items.size(), savedOrder.getId());
        }
    }
}
```

## 5. Update with Optimistic Locking

```java
@Service
public class UserUpdateCommand 
    extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {
    
    public UserUpdateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
    
    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();
    }
    
    @Override
    protected User beforeUpdate(UserUpdateInput input, User existingEntity,
                               CommandHolder<UserUpdateInput> holder) {
        // Check version for optimistic locking
        if (input.getVersion() != null && 
            !input.getVersion().equals(existingEntity.getVersion())) {
            throw new OptimisticLockingException(
                "Entity was modified by another transaction");
        }
        
        return existingEntity;
    }
}
```

## 6. Soft Delete with Audit Trail

```java
@Service
@Slf4j
public class UserDeleteCommand 
    extends BaseCrudDeleteCommand<User, UUID> {
    
    private final AuditLogRepository auditLogRepository;
    
    public UserDeleteCommand(UserRepository repository,
                           AuditLogRepository auditLogRepository) {
        super(repository);
        this.auditLogRepository = auditLogRepository;
    }
    
    @Override
    protected void beforeDelete(UUID id, User entity, CommandHolder<UUID> holder) {
        // Check if user can be deleted
        if (entity.getUsername().equals("admin")) {
            throw new IllegalStateException("Cannot delete admin user");
        }
    }
    
    @Override
    protected void performDelete(UUID id, User entity) {
        // Soft delete
        entity.setActive(false);
        entity.setDeletedAt(LocalDateTime.now());
        repository.save(entity);
    }
    
    @Override
    protected void afterDelete(UUID id, User entity, CommandHolder<UUID> holder) {
        // Create audit log
        AuditLog log = AuditLog.builder()
                .entityType("User")
                .entityId(id.toString())
                .action("DELETE")
                .timestamp(LocalDateTime.now())
                .userId(holder.getContext().getUserId())
                .build();
        
        auditLogRepository.save(log);
        
        log.info("User soft-deleted with audit trail: {}", id);
    }
}
```

## 7. Create with File Upload

```java
@Service
@Slf4j
public class UserCreateWithAvatarCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    private final FileStorageService fileStorage;
    
    public UserCreateWithAvatarCommand(UserRepository repository, 
                                      EntityMapper mapper,
                                      FileStorageService fileStorage) {
        super(repository, mapper);
        this.fileStorage = fileStorage;
    }
    
    @Override
    protected User beforeSave(User entity, UserCreateInput input,
                             CommandHolder<UserCreateInput> holder) {
        // Upload avatar if provided
        if (input.getAvatarFile() != null) {
            String avatarUrl = fileStorage.upload(
                input.getAvatarFile(), 
                "avatars/" + entity.getUsername()
            );
            entity.setAvatar(avatarUrl);
        }
        
        return entity;
    }
}
```

## 8. Update with Event Publishing

```java
@Service
@Slf4j
public class UserUpdateCommand 
    extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {
    
    private final ApplicationEventPublisher eventPublisher;
    
    public UserUpdateCommand(UserRepository repository, 
                           EntityMapper mapper,
                           ApplicationEventPublisher eventPublisher) {
        super(repository, mapper);
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();
    }
    
    @Override
    protected void afterSave(User savedEntity, UserUpdateInput input,
                            CommandHolder<UserUpdateInput> holder) {
        // Publish domain event
        eventPublisher.publishEvent(
            new UserUpdatedEvent(this, savedEntity)
        );
        
        // Send notification if email changed
        if (input.getEmail() != null && 
            !input.getEmail().equals(savedEntity.getEmail())) {
            eventPublisher.publishEvent(
                new UserEmailChangedEvent(this, savedEntity)
            );
        }
        
        log.info("User updated and events published: {}", savedEntity.getId());
    }
}
```

## 9. Conditional Update

```java
@Service
public class UserUpdateCommand 
    extends BaseCrudUpdateCommandV2<User, UserUpdateInput, UUID> {
    
    public UserUpdateCommand(UserRepository repository, EntityMapper mapper) {
        super(repository, mapper);
    }
    
    @Override
    protected UUID extractId(UserUpdateInput input) {
        return input.getId();
    }
    
    @Override
    protected User updateEntity(User entity, UserUpdateInput input,
                               CommandHolder<UserUpdateInput> holder) {
        // Auto-map most fields
        super.updateEntity(entity, input, holder);
        
        // Conditional logic for specific fields
        if (input.getEmail() != null && !input.getEmail().equals(entity.getEmail())) {
            entity.setEmail(input.getEmail());
            entity.setEmailVerified(false); // Reset verification on email change
        }
        
        // Only admin can change roles
        if (input.getRole() != null) {
            String currentUserId = holder.getContext().getUserId();
            if (!isAdmin(currentUserId)) {
                throw new SecurityException("Only admins can change user roles");
            }
            entity.setRole(input.getRole());
        }
        
        return entity;
    }
    
    private boolean isAdmin(String userId) {
        // Implementation
        return false;
    }
}
```

## 10. Batch Update Command

```java
@Service
public class UserBatchUpdateCommand 
    extends BaseCommand<List<UserUpdateInput>, List<User>> 
    implements TransactionalCommand<List<UserUpdateInput>, List<User>> {
    
    private final UserRepository repository;
    private final EntityMapper entityMapper;
    
    public UserBatchUpdateCommand(UserRepository repository, EntityMapper mapper) {
        this.repository = repository;
        this.entityMapper = mapper;
    }
    
    @Override
    protected List<User> onExecute(CommandHolder<List<UserUpdateInput>> holder) {
        List<UserUpdateInput> inputs = holder.getInput();
        
        // Fetch all users in one query
        List<UUID> ids = inputs.stream()
                .map(UserUpdateInput::getId)
                .collect(Collectors.toList());
        
        Map<UUID, User> userMap = repository.findAllById(ids).stream()
                .collect(Collectors.toMap(User::getId, u -> u));
        
        // Update all users
        List<User> updatedUsers = inputs.stream()
                .map(input -> {
                    User user = userMap.get(input.getId());
                    if (user == null) {
                        throw new RuntimeException("User not found: " + input.getId());
                    }
                    entityMapper.map(input, user, Set.of("id", "createdAt", "createdBy"));
                    return user;
                })
                .collect(Collectors.toList());
        
        // Batch save
        return repository.saveAll(updatedUsers);
    }
}
```

## 11. Command with Custom Validation

```java
@Service
public class UserCreateCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    private final Validator validator;
    
    public UserCreateCommand(UserRepository repository, 
                           EntityMapper mapper,
                           Validator validator) {
        super(repository, mapper);
        this.validator = validator;
    }
    
    @Override
    protected void validate(CommandHolder<UserCreateInput> holder) {
        UserCreateInput input = holder.getInput();
        
        // Custom validation rules
        List<String> errors = new ArrayList<>();
        
        if (input.getUsername() == null || input.getUsername().length() < 3) {
            errors.add("Username must be at least 3 characters");
        }
        
        if (input.getEmail() == null || !input.getEmail().contains("@")) {
            errors.add("Valid email is required");
        }
        
        if (input.getPassword() != null && input.getPassword().length() < 8) {
            errors.add("Password must be at least 8 characters");
        }
        
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        
        // Call parent validation if needed
        super.validate(holder);
    }
}
```

## 12. Multi-Step Command with Compensation

```java
@Service
@Slf4j
public class UserCreateWithProfileCommand 
    extends BaseCrudCreateCommandV2<User, UserCreateInput, UUID> {
    
    private final ProfileRepository profileRepository;
    private final NotificationService notificationService;
    
    public UserCreateWithProfileCommand(UserRepository repository, 
                                       EntityMapper mapper,
                                       ProfileRepository profileRepository,
                                       NotificationService notificationService) {
        super(repository, mapper);
        this.profileRepository = profileRepository;
        this.notificationService = notificationService;
    }
    
    @Override
    protected UUID onExecute(CommandHolder<UserCreateInput> holder) {
        UUID userId = null;
        Profile profile = null;
        
        try {
            // Step 1: Create user
            userId = super.onExecute(holder);
            
            // Step 2: Create profile
            profile = Profile.builder()
                    .userId(userId)
                    .displayName(holder.getInput().getUsername())
                    .build();
            profile = profileRepository.save(profile);
            
            // Step 3: Send welcome email
            notificationService.sendWelcomeEmail(userId);
            
            return userId;
            
        } catch (Exception ex) {
            // Compensation logic
            log.error("Failed to create user with profile, rolling back", ex);
            
            if (profile != null) {
                profileRepository.delete(profile);
            }
            if (userId != null) {
                repository.deleteById(userId);
            }
            
            throw new RuntimeException("Failed to create user with profile", ex);
        }
    }
}
```

---

Những ví dụ này demonstrate các patterns phổ biến khi làm việc với CRUD commands trong real-world applications!
