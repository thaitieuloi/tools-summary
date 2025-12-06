# Summary of Changes

## GraphQL Implementation

Đã chuyển đổi từ **GraphQL SPQR** sang **Spring GraphQL** (official Spring support) vì:
- Spring Boot 4.0.0 chưa có SPQR starter tương thích
- Spring GraphQL là giải pháp chính thức, ổn định hơn
- Hoàn toàn tương thích với Spring Boot 4.0.0

## Các file đã tạo/cập nhật:

### 1. build.gradle
- Sử dụng `spring-boot-starter-graphql` thay vì SPQR
- Spring Boot version: 4.0.0

### 2. UserGraphQLController.java
- Controller sử dụng `@QueryMapping` và `@MutationMapping`
- Dễ dàng mở rộng và maintain

### 3. schema.graphqls
- File schema GraphQL chuẩn
- Định nghĩa types, queries, mutations cho User

### 4. application.yaml
- Endpoint: `/api/v1/graphql`  
- GraphiQL UI: `/graphiql`

## Cách sử dụng:

1. Khởi động ứng dụng:
```bash
./gradlew bootRun
```

2. Truy cập GraphiQL: `http://localhost:8080/graphiql`

3. Example queries:
```graphql
query {
  getUser(id: "uuid-here") {
    id
    username
    email
  }
}

mutation {
  createUser(input: {username: "test", email: "test@example.com"}) {
    id
    username
  }
}
```

## Lợi ích:
- ✅ Tương thích 100% với Spring Boot 4.0.0
- ✅ Có GraphiQL UI built-in
- ✅ Annotation-based, dễ đọc
- ✅ Hỗ trợ bởi Spring team
