# 🚀 Quick Start Guide - Multi-Module Project

## Cấu Trúc Hiện Tại

```
tool/
├── api/       ← Main Spring Boot Application
├── core/      ← GraphQL APIs & Services
├── domain/    ← Entities & Repositories  
└── shared/    ← DTOs
```

## Build & Run

```bash
# Build toàn bộ project
./gradlew clean build

# Run ứng dụng
./gradlew :api:bootRun
```

## Test GraphQL API

**URL**: http://localhost:8080/graphiql

### Example Query - Get User
```graphql
query {
  getUser(id: "your-uuid-here") {
    id
    username
    email
    active
  }
}
```

### Example Query - Search Users
```graphql
query {
  searchUsers(
    filter: {
      username: "john"
    }
    page: 0
    size: 10
  ) {
    id
    username
    email
  }
}
```

### Example Mutation -Create User
```graphql
mutation {
  createUser(input: {
    username: "john_doe"
    email: "john@example.com"
  }) {
    id
    username
    email
    createdAt
  }
}
```

### Example Mutation - Update User
```graphql
mutation {
  updateUser(
    id: "your-uuid-here"
    input: {
      username: "john_updated"
      email: "john_new@example.com"
    }
  ) {
    id
    username
    email
    updatedAt
  }
}
```

### Example Mutation - Delete User
```graphql
mutation {
  deleteUser(id: "your-uuid-here")
}
```

## Module Structure

### api (Main Application)
- Entry point: `ToolApiApplication.java`
- Configuration: `application.yml`, `JpaConfig.java`
- Resources: GraphQL schemas, DB migrations

### core (Business Logic)
- GraphQL APIs: `UserGraphQLApi.java`
- Services: `UserService.java`
- Exceptions: `NotFoundException.java`

### domain (Data Model)
- Entities: `User.java`
- Repositories: `UserRepository.java`

### shared (DTOs)
- `UserInput.java`
- `UserSearchInput.java`

## Development Workflow

1. **Thêm Entity mới** → Tạo trong `domain/entity/`
2. **Thêm Repository** → Tạo trong `domain/repository/`
3. **Thêm DTO** → Tạo trong `shared/dto/`
4. **Thêm Service** → Tạo trong `core/service/`
5. **Thêm GraphQL API** → Tạo trong `core/graphql/`
6. **Thêm Schema** → Cập nhật `api/resources/graphql/schema.graphqls`

## Useful Commands

```bash
# Clean build
./gradlew clean

# Build without tests
./gradlew build -x test

# Build specific module
./gradlew :core:build

# Check dependencies
./gradlew :api:dependencies

# List all modules
./gradlew projects
```

## Database Setup

Make sure PostgreSQL is running:
- Host: localhost:5432
- Database: tool
- Username: postgres
- Password: 123456

## Troubleshooting

**Issue**: Module not found
```bash
./gradlew clean build
```

**Issue**: Port 8080 already in use
- Change port in `api/src/main/resources/application.yml`

**Issue**: Database connection failed
- Check PostgreSQL is running
- Verify credentials in `application.yml`

## Next Steps

1. ✅  Project structure set up
2. ✅ Build successful
3. ✅ Application running
4. 📝 Start adding your features!

Happy coding! 🎉
