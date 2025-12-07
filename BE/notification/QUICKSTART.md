# Quick Start Guide - Notification Microservice

## Prerequisites

1. **Java 17** installed
2. **PostgreSQL** installed and running
3. **Common module** built successfully

## Step 1: Create Database

```sql
-- Connect to PostgreSQL
psql -U postgres

-- Create database
CREATE DATABASE notification;

-- Verify
\l
```

## Step 2: Build Common Module

```bash
cd BE/common
./gradlew clean build
```

## Step 3: Build Notification

```bash
cd BE/notification
./gradlew clean build -x test
```

## Step 4: Run Notification Microservice

```bash
./gradlew :api:bootRun
```

Or directly:
```bash
cd 5-api
../gradlew bootRun
```

## Step 5: Access GraphiQL

Open browser: http://localhost:8081/graphiql

## Test Queries

### Query - Find Notification
```graphql
query {
  notificationFindById(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    title
    message
  }
}
```

### Query - List Notifications
```graphql
query {
  notificationList(page: 0, size: 10) {
    id
    title
    message
  }
}
```

### Mutation - Create Notification
```graphql
mutation {
  notificationCreate(
    title: "Test Notification"
    message: "This is a test message"
  )
}
```

### Mutation - Delete Notification
```graphql
mutation {
  notificationDelete(id: "123e4567-e89b-12d3-a456-426614174000")
}
```

## Troubleshooting

### Database Connection Error
- Ensure PostgreSQL is running
- Check credentials in `5-api/src/main/resources/application.yml`
- Verify database `notification` exists

### Port Already in Use
- Default port is 8081
- Change in `application.yml` under `server.port`

### Build Errors
- Ensure Common module is built first
- Run `./gradlew clean build` to clean old artifacts

### Liquibase Errors
- The changelog is currently empty (placeholder)
- This is expected for basic setup
- Add migrations as you develop entities

## Development Workflow

1. **Add Entity**: Create JPA entity class
2. **Add Repository**: Create repository interface
3. **Add Migration**: Create Liquibase changeset
4. **Add Command**: Create command for business logic
5. **Add GraphQL API**: Add query/mutation methods
6. **Test**: Use GraphiQL to test

## Configuration Files

- **Application Config**: `5-api/src/main/resources/application.yml`
- **Liquibase Master**: `5-api/src/main/resources/db/changelog/db.changelog-master.xml`
- **Build Config**: `build.gradle` (root and each module)

## Port Configuration

- Tools: 8080
- Notification: 8081
- GraphQL: `/api/v1/graphql`
- GraphiQL: `/graphiql`
