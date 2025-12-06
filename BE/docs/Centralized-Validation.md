# Centralized Validation với UserValidation

Thay vì viết validation rules rải rác trong các Command, chúng ta sử dụng class `UserValidation` để tập trung toàn bộ logic validation của User.

## Cấu trúc

`com.ttl.tool.core.validation.UserValidation`

```java
public class UserValidation {

    // Rules cho Create
    public static ValidatorBuilder<UserCreateInput> create(UserRepository repo) {
        return ValidatorBuilder.<UserCreateInput>of()
            .constraint(UserCreateInput::getUsername, "username", c -> c.notBlank()...)
            .constraint(UserCreateInput::getEmail, "email", c -> c.email()...)
            // Check unique
            .constraintOnTarget(input -> !repo.existsByUsername(input.getUsername()), ...);
    }

    // Rules cho Update
    public static ValidatorBuilder<UserUpdateInput> update() {
        return ValidatorBuilder.<UserUpdateInput>of()
            .constraint(UserUpdateInput::getId, "id", c -> c.notNull())
            // Validate fields only if present
            .constraint(UserUpdateInput::getUsername, "username", c -> c.lessThanOrEqual(50))
            .constraint(UserUpdateInput::getEmail, "email", c -> c.email());
    }
}
```

## Cách sử dụng trong Command

### UserCreateCommand

```java
@Override
protected ValidatorBuilder<UserCreateInput> getValidatorBuilder(CommandHolder<UserCreateInput> holder) {
    // Gọi rules từ UserValidation
    return UserValidation.create(userRepository);
}
```

### UserUpdateCommand

```java
@Override
protected ValidatorBuilder<UserUpdateInput> getValidatorBuilder(CommandHolder<UserUpdateInput> holder) {
    // Gọi rules từ UserValidation
    return UserValidation.update();
}
```

## Lợi ích

1. **Tái sử dụng**: Rules có thể được dùng ở nhiều nơi (Command, Controller, Service khác).
2. **Dễ bảo trì**: Sửa rule ở một nơi duy nhất.
3. **Gọn gàng**: Command class chỉ tập trung vào business flow, không bị rối bởi validation code.
4. **Testable**: Dễ dàng viết unit test cho `UserValidation` độc lập với Command.
