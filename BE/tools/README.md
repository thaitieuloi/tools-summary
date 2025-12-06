# Multi-Module Gradle Project Structure

## 📁 Project Structure

```
tool/
├── api/                                    # Main Spring Boot Application (Module chính)
│   ├── src/main/java/
│   │   └── com/ttl/tool/api/
│   │       └── ToolApiApplication.java    # Main Application class
│   ├── src/main/resources/
│   │   ├── application.yml                # Configuration
│   │   ├── db/                           # Liquibase migrations
│   │   └── graphql/                      # GraphQL schemas
│   │       └── schema.graphqls
│   └── build.gradle                       # API module dependencies
│
├── core/                                  # Business Logic & GraphQL APIs
│   ├── src/main/java/
│   │   └── com/ttl/tool/core/
│   │       ├── graphql/                  # GraphQL API definitions
│   │       │   └── UserGraphQLApi.java
│   │       ├── service/                  # Business services
│   │       │   └── UserService.java
│   │       └── exception/                # Custom exceptions
│   │           └── NotFoundException.java
│   └── build.gradle                       # Core module dependencies
│
├── domain/                                # Domain Entities & Repositories
│   ├── src/main/java/
│   │   └── com/ttl/tool/domain/
│   │       ├── entity/                   # JPA Entities
│   │       │   └── User.java
│   │       └── repository/               # Spring Data Repositories
│   │           └── UserRepository.java
│   └── build.gradle                       # Domain module dependencies
│
├── shared/                                # Shared DTOs
│   ├── src/main/java/
│   │   └── com/ttl/tool/shared/dto/
│   │       ├── UserInput.java
│   │       └── UserSearchInput.java
│   └── build.gradle                       # Shared module dependencies
│
├── build.gradle                           # Root build configuration
└── settings.gradle                        # Multi-module settings
```

## 🎯 Module Dependencies

```
api
 ├── depends on → core
 │                 ├── depends on → domain
 │                 └── depends on → shared
 └── (transitively gets domain & shared)

Dependency Flow:
api → core → domain
         └→ shared
```

## 📦 Module Descriptions

### 1. **api** - Main Application Module
- **Purpose**: Entry point của ứng dụng Spring Boot
- **Type**: Executable (bootable JAR)
- **Contains**:
  - `ToolApiApplication.java` - Main application class
  - Configuration files (application.yml)
  - Database migrations (Liquibase)
  - GraphQL schema definitions
- **Dependencies**: core (which includes domain & shared)

### 2. **core** - Business Logic Module
- **Purpose**: Chứa GraphQL APIs và business logic
- **Type**: Library (JAR)
- **Contains**:
  - `graphql/` - GraphQL controllers với @QueryMapping và @MutationMapping
  - `service/` - Business services
  - `exception/` - Custom exceptions
- **Dependencies**: domain, shared

### 3. **domain** - Domain Model Module
- **Purpose**: Chứa entities và repositories
- **Type**: Library (JAR)
- **Contains**:
  - `entity/` - JPA entities (User, etc.)
  - `repository/` - Spring Data JPA repositories
- **Dependencies**: Spring Data JPA, PostgreSQL driver

### 4. **shared** - Shared DTOs Module
- **Purpose**: Chứa Data Transfer Objects được dùng chung
- **Type**: Library (JAR)
- **Contains**:
  - `dto/` - Input/Output DTOs
- **Dependencies**: Lombok only

## 🚀 How to Build & Run

### Build entire project:
```bash
./gradlew clean build
```

### Build specific module:
```bash
./gradlew :api:build
./gradlew :core:build
```

### Run application:
```bash
./gradlew :api:bootRun
```

### Test:
```bash
./gradlew test
```

## 📝 How to Add New Features

### Example: Adding Product entity

#### 1. Create Entity in `domain` module:
```java
// domain/src/main/java/com/ttl/tool/domain/entity/Product.java
@Entity
@Table(name = "products")
public class Product {
    // ...
}
```

#### 2. Create Repository in `domain` module:
```java
// domain/src/main/java/com/ttl/tool/domain/repository/ProductRepository.java
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
```

#### 3. Create DTOs in `shared` module:
```java
// shared/src/main/java/com/ttl/tool/shared/dto/ProductInput.java
@Data
public class ProductInput {
    // ...
}
```

#### 4. Create Service in `core` module:
```java
// core/src/main/java/com/ttl/tool/core/service/ProductService.java
@Service
public class ProductService {
    // ...
}
```

#### 5. Create GraphQL API in `core` module:
```java
// core/src/main/java/com/ttl/tool/core/graphql/ProductGraphQLApi.java
@Controller
public class ProductGraphQLApi {
    @QueryMapping
    public Product getProduct(@Argument String id) { }
    
    @MutationMapping
    public Product createProduct(@Argument ProductInput input) { }
}
```

#### 6. Add GraphQL Schema in `api` module:
```graphql
# api/src/main/resources/graphql/schema.graphqls
type Product {
    id: ID!
    name: String!
}

type Query {
    getProduct(id: ID!): Product
}
```

## ✅ Benefits of Multi-Module Architecture

1. **Separation of Concerns**: Mỗi module có trách nhiệm rõ ràng
2. **Reusability**: Có thể tái sử dụng modules (shared, domain) trong projects khác
3. **Independent Development**: Teams có thể làm việc trên các modules khác nhau
4. **Build Optimization**: Gradle chỉ rebuild modules bị thay đổi
5. **Clean Dependencies**: Dependency flow rõ ràng, tránh circular dependencies
6. **Testability**: Dễ dàng test từng module độc lập

## 📊 Module Sizes & Responsibilities

| Module | Size | Type | Main Responsibility |
|--------|------|------|---------------------|
| **api** | Medium | Executable | Main application, configuration |
| **core** | Large | Library | Business logic, GraphQL APIs |
| **domain** | Medium | Library | Data model, repositories |
| **shared** | Small | Library | DTOs, common utils |

## 🔧 Configuration Notes

### Component Scanning
Main application (`ToolApiApplication.java`) cấu hình scan:
```java
@SpringBootApplication(scanBasePackages = {
    "com.ttl.tool.api",
    "com.ttl.tool.core"
})
@EnableJpaRepositories(basePackages = "com.ttl.tool.domain.repository")
@EntityScan(basePackages = "com.ttl.tool.domain.entity")
```

### Build Configuration
- **Root `build.gradle`**: Cấu hình chung cho tất cả subprojects
- **Module `build.gradle`**: Dependencies riêng của từng module
- **settings.gradle**: Liệt kê tất cả modules

## 🎉 Migration Complete!

Old single-module structure đã được chuyển thành multi-module structure:
- ✅ All code moved to appropriate modules
- ✅ Dependencies configured correctly
- ✅ Build and runtime tested
- ✅ Ready for development

## 📚 Next Steps

1. Test GraphQL endpoints: http://localhost:8080/graphiql
2. Add more entities following the pattern above
3. Configure CI/CD for multi-module build
4. Consider adding integration tests module
