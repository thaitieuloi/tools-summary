# Project API Documentation

## Tổng quan

API cơ bản cho quản lý Project với đầy đủ các chức năng CRUD (Create, Read, Update, Delete).

## Thông tin Entity

### Project Entity

Entity `Project` bao gồm các trường sau:

#### Các trường cơ bản:
- `id` (UUID) - ID duy nhất của project, tự động sinh
- `name` (String) - Tên project (bắt buộc, tối đa 255 ký tự)
- `description` (String) - Mô tả project (tùy chọn, tối đa 1000 ký tự)
- `status` (String) - Trạng thái project (mặc định: "ACTIVE")
  - Các giá trị hợp lệ: `ACTIVE`, `INACTIVE`, `COMPLETED`, `ARCHIVED`

#### Trường URN - Tự động build động:
- `urn` (String) - URN của project theo format: `tools:project:{id}`
  - Trường này được tự động build khi entity được load, create hoặc update
  - Ví dụ: `tools:project:123e4567-e89b-12d3-a456-426614174000`

#### Các trường audit (tự động quản lý):
- `createdAt` (LocalDateTime) - Thời gian tạo
- `updatedAt` (LocalDateTime) - Thời gian cập nhật lần cuối
- `createdBy` (String) - Người tạo
- `updatedBy` (String) - Người cập nhật lần cuối
- `active` (boolean) - Trạng thái active/inactive (mặc định: true)

## GraphQL API

### Queries (Truy vấn)

#### 1. Lấy project theo ID

```graphql
query {
  projectFindById(id: "uuid-here") {
    id
    name
    description
    status
    urn
    createdAt
    updatedAt
    active
  }
}
```

#### 2. Lấy project theo tên

```graphql
query {
  projectFindByName(name: "My Project") {
    id
    name
    description
    urn
  }
}
```

#### 3. Tìm kiếm projects với filter

```graphql
query {
  projectSearch(
    filter: {
      status: "ACTIVE"
      active: true
    }
    page: 0
    size: 10
  ) {
    id
    name
    description
    status
    urn
    createdAt
  }
}
```

#### 4. Lấy tất cả projects đang active

```graphql
query {
  projectFindAllActive(page: 0, size: 20) {
    id
    name
    urn
    status
  }
}
```

### Mutations (Thay đổi)

#### 1. Tạo project mới

```graphql
mutation {
  projectCreate(input: {
    name: "New Project"
    description: "This is a new project"
    status: "ACTIVE"
  })
}
```

**Response**: Trả về UUID của project vừa tạo

**Validation rules**:
- `name`: Bắt buộc, không được rỗng, tối đa 255 ký tự, phải unique
- `description`: Tùy chọn, tối đa 1000 ký tự
- `status`: Phải là một trong: ACTIVE, INACTIVE, COMPLETED, ARCHIVED
- URN sẽ tự động được tạo sau khi project được lưu

#### 2. Cập nhật project

```graphql
mutation {
  projectUpdate(
    id: "uuid-here"
    input: {
      name: "Updated Project Name"
      description: "Updated description"
      status: "COMPLETED"
    }
  ) {
    id
    name
    description
    status
    urn
    updatedAt
  }
}
```

**Response**: Trả về project đã được cập nhật

**Lưu ý**:
- Tất cả các trường trong input đều là tùy chọn
- URN sẽ tự động được cập nhật nếu có thay đổi

#### 3. Xóa project

```graphql
mutation {
  projectDelete(id: "uuid-here")
}
```

**Response**: Trả về `true` nếu xóa thành công

## Kiến trúc Code

### Cấu trúc thư mục:

```
tools/
├── 1-domain/              # Domain layer
│   └── com.ttl.tool.domain/
│       ├── entity/
│       │   └── Project.java           # Entity với URN auto-build
│       └── repository/
│           └── ProjectRepository.java # JPA Repository
│
├── 1-core/                # Core/Business layer
│   └── com.ttl.tool.core/
│       ├── command/
│       │   └── project/
│       │       ├── ProjectCreateCommand.java  # Command tạo mới
│       │       ├── ProjectUpdateCommand.java  # Command cập nhật
│       │       └── ProjectDeleteCommand.java  # Command xóa
│       ├── dto/
│       │   └── input/
│       │       ├── ProjectCreateInput.java    # DTO cho create
│       │       └── ProjectUpdateInput.java    # DTO cho update
│       ├── validation/
│       │   └── ProjectValidation.java         # YAVI validation rules
│       └── graphql/
│           └── ProjectGraphQLApi.java         # GraphQL API endpoints
│
└── 1-shared/              # Shared DTOs
    └── com.ttl.tool.shared/
        └── dto/
            └── ProjectSearchInput.java        # DTO cho search filter
```

### Pattern được sử dụng:

1. **Command Pattern**: Mỗi operation (Create, Update, Delete) có một Command class riêng
2. **Repository Pattern**: Sử dụng Spring Data JPA Repository
3. **DTO Pattern**: Input/Output được phân tách với Entity
4. **Validation**: Sử dụng YAVI framework để validate input
5. **Auto-mapping**: Sử dụng EntityMapper để tự động map giữa DTO và Entity

### Chi tiết về URN field:

URN field được build tự động thông qua JPA lifecycle callbacks:

```java
@PostLoad    // Sau khi load từ database
@PostPersist // Sau khi insert vào database
@PostUpdate  // Sau khi update trong database
public void buildUrn() {
    if (this.id != null) {
        this.urn = "tools:project:" + this.id;
    }
}
```

Pattern: `tools:project:{id}`
- `tools`: Module name
- `project`: Entity type
- `{id}`: UUID của entity

## Ví dụ sử dụng hoàn chỉnh

### 1. Tạo project mới và lấy URN

```graphql
# Step 1: Tạo project
mutation {
  projectCreate(input: {
    name: "E-Commerce Platform"
    description: "Building a new e-commerce platform"
    status: "ACTIVE"
  })
}

# Response: "123e4567-e89b-12d3-a456-426614174000"

# Step 2: Lấy thông tin project kèm URN
query {
  projectFindById(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    name
    urn  # Sẽ trả về: "tools:project:123e4567-e89b-12d3-a456-426614174000"
    createdAt
  }
}
```

### 2. Tìm kiếm và cập nhật projects

```graphql
# Step 1: Tìm các projects đang ACTIVE
query {
  projectSearch(filter: { status: "ACTIVE" }, page: 0, size: 10) {
    id
    name
    urn
    status
  }
}

# Step 2: Cập nhật status thành COMPLETED
mutation {
  projectUpdate(
    id: "123e4567-e89b-12d3-a456-426614174000"
    input: {
      status: "COMPLETED"
    }
  ) {
    id
    name
    status
    urn  # URN vẫn giữ nguyên format
    updatedAt
  }
}
```

## Validation Rules

### Create Project:
- ✅ `name`: Required, not blank, <= 255 characters, must be unique
- ✅ `description`: Optional, <= 1000 characters
- ✅ `status`: Must be one of: ACTIVE, INACTIVE, COMPLETED, ARCHIVED
- ✅ `active`: Optional, default = true

### Update Project:
- ✅ `id`: Required
- ✅ `name`: Optional, <= 255 characters
- ✅ `description`: Optional, <= 1000 characters
- ✅ `status`: Optional, must be one of: ACTIVE, INACTIVE, COMPLETED, ARCHIVED

## Testing với GraphiQL

Truy cập GraphiQL interface tại: `http://localhost:8080/graphiql`

### Test Case 1: CRUD Flow hoàn chỉnh

```graphql
# 1. Create
mutation CreateProject {
  projectCreate(input: {
    name: "Test Project"
    description: "This is a test"
    status: "ACTIVE"
  })
}

# 2. Read
query GetProject {
  projectFindById(id: "{id-from-create}") {
    id
    name
    urn
  }
}

# 3. Update
mutation UpdateProject {
  projectUpdate(id: "{id-from-create}", input: {
    status: "COMPLETED"
  }) {
    id
    status
    urn
  }
}

# 4. Delete
mutation DeleteProject {
  projectDelete(id: "{id-from-create}")
}
```

## Error Handling

API sẽ trả về các lỗi sau trong các trường hợp:

1. **Validation Error**: Khi input không hợp lệ
   ```json
   {
     "errors": [{
       "message": "Validation failed",
       "extensions": {
         "violations": [
           {
             "property": "name",
             "message": "Project name is required"
           }
         ]
       }
     }]
   }
   ```

2. **Not Found Error**: Khi không tìm thấy project
   ```json
   {
     "errors": [{
       "message": "Project not found with id: {id}"
     }]
   }
   ```

3. **Duplicate Error**: Khi tên project đã tồn tại
   ```json
   {
     "errors": [{
       "message": "Project name already exists"
     }]
   }
   ```

## Dependencies

Project sử dụng các dependencies chính:
- Spring Boot Data JPA
- GraphQL SPQR
- YAVI (Validation)
- Lombok
- PostgreSQL/H2 Database

## Running the Application

```bash
# Build project
./gradlew build

# Run application
./gradlew bootRun

# Access GraphiQL
# Open browser: http://localhost:8080/graphiql
```
