# Notification Microservice

Notification microservice sử dụng package root thống nhất: `com.ttl.tool.notification`

## Cấu trúc Module

```
notification/
├── 0-base/           # Base configurations, JPA entities base classes
├── 1-core/           # Core business logic, GraphQL APIs, Commands
├── 1-shared/         # Shared DTOs và classes
├── 5-api/            # Main Spring Boot application (Bootable)
├── 5-migrated-api/   # Legacy API migrations
└── 5-migrated-db/    # Legacy database migrations
```

## Kiến trúc

- **Common Module**: Notification sử dụng common module (composite build) cho các chức năng dùng chung
- **Package Root**: `com.ttl.tool.notification` - thống nhất với tools microservice
- **GraphQL API**: Sử dụng SPQR cho GraphQL endpoint tại `/api/v1/graphql`
- **Database**: PostgreSQL (port 5432, database: notification)
- **Server Port**: 8081

## Chạy ứng dụng

### Build project
```bash
./gradlew clean build
```

### Chạy application
```bash
./gradlew :api:bootRun
```

Hoặc:
```bash
cd 5-api
../gradlew bootRun
```

## GraphQL API

Sau khi chạy, truy cập GraphiQL tại: http://localhost:8081/graphiql

### Ví dụ Queries

```graphql
# Lấy thông tin notification
query {
  notificationFindById(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    title
    message
  }
}

# List notifications
query {
  notificationList(page: 0, size: 10) {
    id
    title
    message
  }
}
```

### Ví dụ Mutations

```graphql
# Tạo notification mới
mutation {
  notificationCreate(
    title: "New Notification"
    message: "This is a test notification"
  )
}

# Xóa notification
mutation {
  notificationDelete(id: "123e4567-e89b-12d3-a456-426614174000")
}
```

## Database Setup

Tạo database:
```sql
CREATE DATABASE notification;
```

Liquibase sẽ tự động chạy migrations khi khởi động ứng dụng.

## Dependencies

- Spring Boot 4.0.0
- GraphQL Java 20.0
- PostgreSQL 42.7.3
- Liquibase
- YAVI Validation 0.14.1
- Lombok

## Phát triển tiếp

### Thêm Entity mới
1. Tạo entity trong `1-shared` hoặc tạo module `1-domain` riêng
2. Tạo Repository trong `0-base`
3. Tạo Commands trong `1-core/command`
4. Tạo GraphQL API trong `1-core/graphql`

### Thêm Migration
1. Tạo file XML trong `5-api/src/main/resources/db/changelog/`
2. Include vào `db.changelog-master.xml`

## Notes

- Đây là cấu trúc cơ bản, placeholder implementation
- Cần implement thêm entities, repositories, và commands theo yêu cầu thực tế
- Sử dụng Command Pattern giống tools microservice
