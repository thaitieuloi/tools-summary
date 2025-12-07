# GraphQL Example Queries for Project API

## 📝 Mutations (Create, Update, Delete)

### 1. Create Project - Simple
```graphql
mutation CreateProject {
  projectCreate(input: {
    name: "E-Commerce Platform"
    description: "Building a scalable e-commerce platform"
    status: "ACTIVE"
  })
}
```

**Expected Response:**
```json
{
  "data": {
    "projectCreate": "123e4567-e89b-12d3-a456-426614174000"
  }
}
```

---

### 2. Create Project - Minimal (chỉ name)
```graphql
mutation CreateMinimalProject {
  projectCreate(input: {
    name: "Quick Project"
  })
}
```
*Note: `status` mặc định là "ACTIVE", `active` mặc định là `true`*

---

### 3. Update Project - Full Update
```graphql
mutation UpdateProject {
  projectUpdate(
    id: "123e4567-e89b-12d3-a456-426614174000"
    input: {
      name: "E-Commerce Platform v2"
      description: "Updated description with new features"
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

---

### 4. Update Project - Partial Update (chỉ status)
```graphql
mutation UpdateProjectStatus {
  projectUpdate(
    id: "123e4567-e89b-12d3-a456-426614174000"
    input: {
      status: "ARCHIVED"
    }
  ) {
    id
    name
    status
    urn
  }
}
```

---

### 5. Delete Project
```graphql
mutation DeleteProject {
  projectDelete(id: "123e4567-e89b-12d3-a456-426614174000")
}
```

---

## 🔍 Queries (Read)

### 1. Find Project by ID (với URN)
```graphql
query GetProjectById {
  projectFindById(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    name
    description
    status
    urn
    createdAt
    updatedAt
    createdBy
    updatedBy
    active
  }
}
```

**Expected Response with URN:**
```json
{
  "data": {
    "projectFindById": {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "name": "E-Commerce Platform",
      "description": "Building a scalable e-commerce platform",
      "status": "ACTIVE",
      "urn": "tools:project:123e4567-e89b-12d3-a456-426614174000",
      "createdAt": "2025-12-06T10:00:00",
      "updatedAt": null,
      "createdBy": null,
      "updatedBy": null,
      "active": true
    }
  }
}
```

---

### 2. Find Project by Name
```graphql
query GetProjectByName {
  projectFindByName(name: "E-Commerce Platform") {
    id
    name
    urn
    status
    createdAt
  }
}
```

---

### 3. Search Projects - All Active
```graphql
query SearchActiveProjects {
  projectSearch(
    filter: {
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

---

### 4. Search Projects - By Status
```graphql
query SearchProjectsByStatus {
  projectSearch(
    filter: {
      status: "ACTIVE"
    }
    page: 0
    size: 20
  ) {
    id
    name
    status
    urn
  }
}
```

---

### 5. Search All Projects - Paginated
```graphql
query SearchAllProjects {
  projectSearch(
    page: 0
    size: 10
  ) {
    id
    name
    description
    status
    urn
    active
    createdAt
    updatedAt
  }
}
```

---

### 6. Find All Active Projects
```graphql
query GetActiveProjects {
  projectFindAllActive(page: 0, size: 10) {
    id
    name
    urn
    status
  }
}
```

---

## 🧪 Complete CRUD Test Flow

### Step 1: Create a New Project
```graphql
mutation Step1_Create {
  projectCreate(input: {
    name: "Test Project Flow"
    description: "Testing complete CRUD operations"
    status: "ACTIVE"
  })
}
```
*Copy the returned UUID for next steps*

---

### Step 2: Read the Created Project (verify URN)
```graphql
query Step2_Read {
  projectFindById(id: "YOUR_UUID_HERE") {
    id
    name
    description
    status
    urn  # Should be "tools:project:YOUR_UUID_HERE"
    createdAt
    active
  }
}
```

---

### Step 3: Update the Project
```graphql
mutation Step3_Update {
  projectUpdate(
    id: "YOUR_UUID_HERE"
    input: {
      description: "Updated description after creation"
      status: "COMPLETED"
    }
  ) {
    id
    name
    description
    status
    urn  # URN remains the same
    updatedAt
  }
}
```

---

### Step 4: Delete the Project
```graphql
mutation Step4_Delete {
  projectDelete(id: "YOUR_UUID_HERE")
}
```

---

### Step 5: Verify Deletion (should return error)
```graphql
query Step5_Verify {
  projectFindById(id: "YOUR_UUID_HERE") {
    id
    name
  }
}
```
*Expected: Error "Project not found"*

---

## ⚠️ Validation Test Cases

### 1. Test Required Field (name)
```graphql
mutation TestRequiredName {
  projectCreate(input: {
    description: "No name provided"
  })
}
```
*Expected Error: "Project name is required"*

---

### 2. Test Name Length Limit
```graphql
mutation TestNameLength {
  projectCreate(input: {
    name: "This is a very long project name that exceeds the maximum allowed length of 255 characters and should fail validation. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris."
  })
}
```
*Expected Error: "Project name must not exceed 255 characters"*

---

### 3. Test Duplicate Name
```graphql
# First, create a project
mutation CreateFirst {
  projectCreate(input: {
    name: "Unique Project"
  })
}

# Then try to create another with same name
mutation CreateDuplicate {
  projectCreate(input: {
    name: "Unique Project"
  })
}
```
*Expected Error: "Project name already exists"*

---

### 4. Test Invalid Status
```graphql
mutation TestInvalidStatus {
  projectCreate(input: {
    name: "Test Invalid Status"
    status: "INVALID_STATUS"
  })
}
```
*Expected Error: "Status must be one of: ACTIVE, INACTIVE, COMPLETED, ARCHIVED"*

---

## 📊 Advanced Query Examples

### 1. Get Project with Only Essential Fields
```graphql
query GetProjectEssentials {
  projectFindById(id: "YOUR_UUID_HERE") {
    id
    name
    urn
  }
}
```

---

### 2. Get Multiple Projects with URNs
```graphql
query GetMultipleProjects {
  projectSearch(page: 0, size: 5) {
    id
    name
    urn
    status
  }
}
```

---

### 3. Filter Completed Projects
```graphql
query GetCompletedProjects {
  projectSearch(
    filter: {
      status: "COMPLETED"
    }
    page: 0
    size: 100
  ) {
    id
    name
    urn
    createdAt
    updatedAt
  }
}
```

---

## 🎯 URN Field Testing

### Verify URN Format
```graphql
query VerifyURNFormat {
  projectFindById(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    urn
  }
}
```

**Expected URN Pattern:**
```
urn = "tools:project:" + id
```

**Example:**
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "urn": "tools:project:123e4567-e89b-12d3-a456-426614174000"
}
```

---

### Verify URN After Update
```graphql
mutation UpdateAndCheckURN {
  projectUpdate(
    id: "123e4567-e89b-12d3-a456-426614174000"
    input: {
      name: "Updated Name"
    }
  ) {
    id
    name
    urn  # Should remain: "tools:project:123e4567-e89b-12d3-a456-426614174000"
  }
}
```

---

## 💡 Tips for Testing

1. **Copy UUID**: Sau khi create, copy UUID để dùng cho các queries khác
2. **Use Variables**: GraphiQL hỗ trợ variables để test dễ hơn
3. **Check URN**: Luôn verify URN format sau create/update
4. **Test Pagination**: Thử các giá trị page/size khác nhau
5. **Validation**: Test tất cả validation rules

---

## 🔗 GraphiQL Access

URL: `http://localhost:8080/graphiql`

### How to Use:
1. Start application: `./gradlew bootRun`
2. Open browser: `http://localhost:8080/graphiql`
3. Copy-paste queries from this file
4. Click "Execute Query" or press Ctrl+Enter
5. View results in right panel

---

## 📈 Performance Testing

### Create Multiple Projects
```graphql
mutation CreateMultiple1 {
  p1: projectCreate(input: { name: "Project 1" })
}

mutation CreateMultiple2 {
  p2: projectCreate(input: { name: "Project 2" })
}

mutation CreateMultiple3 {
  p3: projectCreate(input: { name: "Project 3" })
}
```

### Retrieve All with URNs
```graphql
query GetAllProjects {
  projectSearch(page: 0, size: 100) {
    id
    name
    urn
  }
}
```

---

## 🚀 Quick Start Commands

```bash
# 1. Terminal 1: Start application
cd d:\CNTT\tool\BE\tools
./gradlew bootRun

# 2. Terminal 2: Or build first
./gradlew build
java -jar 5-api/build/libs/api.jar

# 3. Browser: Open GraphiQL
# http://localhost:8080/graphiql

# 4. Copy queries from this file and test!
```

Enjoy testing! 🎉
