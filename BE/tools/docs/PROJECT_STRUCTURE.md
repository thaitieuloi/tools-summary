# Project Structure

## Overview
This document describes the new reorganized project structure following clean architecture principles.

## Package Structure

```
com.ttl.tool
├── api/                    # API Layer - Main entry points
│   └── GraphQLController.java
│
├── core/                   # Core Layer - GraphQL API definitions
│   └── UserGraphQLApi.java
│
├── domain/                 # Domain Layer - Business entities and repositories
│   ├── entity/
│   │   └── User.java
│   └── repository/
│       └── UserRepository.java
│
├── shared/                 # Shared Layer - DTOs and common utilities
│   └── dto/
│       ├── UserInput.java
│       └── UserSearchInput.java
│
├── service/                # Service Layer - Business logic
│   └── UserService.java
│
└── exception/              # Exception handling
    └── NotFoundException.java
```

## Layer Descriptions

### 1. API Layer (`api/`)
- **Purpose**: Main entry points for external communication
- **Contains**: Controllers and API configurations
- **Example**: `GraphQLController.java` - Main GraphQL controller that registers all GraphQL APIs

### 2. Core Layer (`core/`)
- **Purpose**: Contains specific GraphQL API definitions
- **Contains**: GraphQL query and mutation mappings
- **Example**: `UserGraphQLApi.java` - Defines all GraphQL operations for User entity

### 3. Domain Layer (`domain/`)
- **Purpose**: Core business domain objects
- **Contains**:
  - `entity/` - JPA entities representing database tables
  - `repository/` - Data access interfaces extending Spring Data JPA

### 4. Shared Layer (`shared/`)
- **Purpose**: Shared components used across layers
- **Contains**:
  - `dto/` - Data Transfer Objects for API communication

### 5. Service Layer (`service/`)
- **Purpose**: Business logic implementation
- **Contains**: Service classes that orchestrate domain operations

## Migration Notes

The following files have been moved:

| Old Location | New Location |
|--------------|--------------|
| `model/User.java` | `domain/entity/User.java` |
| `repository/UserRepository.java` | `domain/repository/UserRepository.java` |
| `dto/UserInput.java` | `shared/dto/UserInput.java` |
| `dto/UserSearchInput.java` | `shared/dto/UserSearchInput.java` |
| `controller/UserGraphQLController.java` | `core/UserGraphQLApi.java` |

## Benefits of New Structure

1. **Clear Separation of Concerns**: Each layer has a specific responsibility
2. **Scalability**: Easy to add new features following the same pattern
3. **Maintainability**: Related code is grouped together
4. **Testability**: Layers can be tested independently
5. **Clean Architecture**: Follows industry best practices

## Adding New Features

To add a new entity (e.g., Product):

1. Create entity in `domain/entity/Product.java`
2. Create repository in `domain/repository/ProductRepository.java`
3. Create DTOs in `shared/dto/ProductInput.java`, etc.
4. Create service in `service/ProductService.java`
5. Create GraphQL API in `core/ProductGraphQLApi.java`
6. Register in `api/GraphQLController.java` if needed
