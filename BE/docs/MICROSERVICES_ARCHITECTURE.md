# Cấu Trúc Microservices - Tool Project

## Tổng Quan Kiến Trúc

```
BE/
├── common/           # Module dùng chung cho tất cả microservices
│   ├── 0-base/      # Base configurations, JPA base classes
│   ├── 1-core/      # Core command pattern, validation
│   └── 2-graphql/   # GraphQL common configurations
│
├── tools/           # Tools Microservice (Port 8080)
│   ├── 1-base/      # Base configurations
│   ├── 1-core/      # Business logic, Commands, GraphQL APIs
│   ├── 1-domain/    # Domain entities
│   ├── 1-shared/    # DTOs
│   └── 5-api/       # Main Spring Boot Application
│
└── notification/    # Notification Microservice (Port 8081)
    ├── 0-base/      # Base configurations
    ├── 1-core/      # Business logic, Commands, GraphQL APIs
    ├── 1-shared/    # DTOs
    ├── 5-api/       # Main Spring Boot Application
    ├── 5-migrated-api/    # Legacy API migrations
    └── 5-migrated-db/     # Legacy database migrations
```

## Package Structure

### Thống Nhất Package Root

Tất cả microservices sử dụng package root: **`com.ttl.tool`**

- **Common**: `com.ttl.common.*`
- **Tools**: `com.ttl.tool.*`
- **Notification**: `com.ttl.tool.notification.*`

### Ví dụ Package Structure

```
com.ttl.tool.notification/
├── api/                          # 5-api module
│   └── NotificationApiApplication.java
│
├── core/                         # 1-core module
│   ├── graphql/
│   │   └── NotificationGraphQLApi.java
│   ├── command/                  # Command pattern (Future)
│   └── validation/               # Validation logic (Future)
│
├── shared/                       # 1-shared module
│   └── dto/                      # Data Transfer Objects
│
├── migratedapi/                  # 5-migrated-api module
│   └── core/
│
└── migrateddb/                   # 5-migrated-db module
    └── entity/
```

## Dependency Flow

```
┌─────────────────────────────────────────────┐
│            Common Module                     │
│  (Base, Core, GraphQL configurations)       │
└─────────────────────────────────────────────┘
                    ↑
         ┌──────────┴──────────┐
         │                     │
    ┌────┴─────┐        ┌─────┴─────┐
    │  Tools   │        │Notification│
    │  API     │        │    API     │
    │ (8080)   │        │   (8081)   │
    └──────────┘        └───────────┘
```

## Build & Run

### Build Common Module (Required First)
```bash
cd BE/common
./gradlew clean build
```

### Build & Run Tools Microservice
```bash
cd BE/tools
./gradlew clean build
./gradlew :api:bootRun
```

Access: http://localhost:8080/graphiql

### Build & Run Notification Microservice
```bash
cd BE/notification
./gradlew clean build
./gradlew :api:bootRun
```

Access: http://localhost:8081/graphiql

## Technology Stack

### Common Stack
- Java 17
- Spring Boot 4.0.0
- GraphQL Java 20.0
- GraphQL SPQR
- PostgreSQL 42.7.3
- Liquibase
- YAVI Validation 0.14.1
- Lombok

### Database Configuration

**Tools Database:**
- Host: localhost:5432
- Database: tool
- Username: postgres
- Password: 123456

**Notification Database:**
- Host: localhost:5432
- Database: notification
- Username: postgres
- Password: 123456

## GraphQL Endpoints

### Tools Microservice
- GraphQL Endpoint: http://localhost:8080/api/v1/graphql
- GraphiQL UI: http://localhost:8080/graphiql

**Available Operations:**
- `userFindById(id: String)`: Get user by ID
- `userSearch(filter: UserSearchInput, page: Int, size: Int)`: Search users
- `userCreate(input: UserCreateInput)`: Create new user
- `userUpdate(id: String, input: UserUpdateInput)`: Update user
- `userDelete(id: String)`: Delete user

### Notification Microservice
- GraphQL Endpoint: http://localhost:8081/api/v1/graphql
- GraphiQL UI: http://localhost:8081/graphiql

**Available Operations (Placeholder):**
- `notificationFindById(id: String)`: Get notification by ID
- `notificationList(page: Int, size: Int)`: List notifications
- `notificationCreate(title: String, message: String)`: Create notification
- `notificationDelete(id: String)`: Delete notification

## Development Guidelines

### Adding New Features to Notification

1. **Add Entity** (if needed):
   - Create in `1-shared` or new `1-domain` module
   - Define JPA entity with `@Entity`

2. **Add Repository**:
   - Create in `0-base` or `1-core`
   - Extend `JpaRepository<Entity, ID>`

3. **Add Command** (for mutations):
   - Create in `1-core/command`
   - Extend `BaseCrudCreateCommand`, `BaseCrudUpdateCommand`, etc.
   - Implement validation logic

4. **Add GraphQL API**:
   - Create in `1-core/graphql`
   - Use `@GraphQLApi`, `@GraphQLQuery`, `@GraphQLMutation`
   - Inject commands and repositories

5. **Add Database Migration**:
   - Create XML in `5-api/src/main/resources/db/changelog/`
   - Include in `db.changelog-master.xml`

### Module Responsibilities

| Module | Purpose | Bootable? |
|--------|---------|-----------|
| 0-base | JPA configurations, entity base classes | No |
| 1-core | Business logic, commands, GraphQL APIs | No |
| 1-shared | DTOs, shared classes | No |
| 1-domain | Domain entities (for tools) | No |
| 5-api | Main Spring Boot application | Yes |
| 5-migrated-api | Legacy API support | No |
| 5-migrated-db | Legacy database entities | No |

## Architecture Patterns

### Command Pattern
- All mutations use Command Pattern
- Commands handle validation and business logic
- Example: `UserCreateCommand`, `UserUpdateCommand`

### Validation
- Uses YAVI library for fluent validation
- Centralized in command classes
- Example: `UserValidation.createValidator()`

### GraphQL Schema Generation
- Auto-generated using SPQR
- Type-safe with Java classes
- No manual schema files needed

## Next Steps for Notification

- [ ] Add real entities (NotificationEntity, etc.)
- [ ] Create repositories
- [ ] Implement Command pattern for CRUD operations
- [ ] Add validation logic with YAVI
- [ ] Create database migrations with Liquibase
- [ ] Implement business logic for notifications
- [ ] Add authentication/authorization
- [ ] Add WebSocket support for real-time notifications
- [ ] Integrate with Tools microservice if needed

## Notes

- **Notification** is currently in basic/placeholder state
- All old `com.glohow.notification` packages have been removed
- Ready for incremental development
- Follow Tools microservice patterns for consistency
