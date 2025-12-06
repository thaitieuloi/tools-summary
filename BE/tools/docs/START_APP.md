# Hướng dẫn khởi động ứng dụng

## ⚠️ Vấn đề hiện tại
Ổ đĩa C: đã hết dung lượng, Gradle không thể build:
```
Caused by: java.io.IOException: There is not enough space on the disk
```

## ✅ Giải pháp

### Cách 1: Chạy từ IDE (KHUYẾN NGHỊ)
1. Mở IntelliJ IDEA hoặc Eclipse
2. Mở file `ToolApplication.java`
3. Click chuột phải → **Run 'ToolApplication'**
4. Application sẽ start lên

### Cách 2: Dọn dẹp ổ đĩa C:
```powershell
# Xóa Gradle cache cũ
rd /s /q "%USERPROFILE%\.gradle\caches"

# Xóa build folder
cd d:\CNTT\tool\BE\tools
rd /s /q build

# Sau đó build lại
gradlew.bat clean build -x test
```

### Cách 3: Chuyển Gradle cache sang ổ khác
Tạo file `gradle.properties` trong thư mục dự án:
```properties
org.gradle.daemon=false
org.gradle.caching=false
```

## 🚀 Sau khi start thành công

**Endpoints:**
- GraphQL API: `http://localhost:8080/api/v1/graphql`
- GraphiQL UI: `http://localhost:8080/graphiql`

**Test query:**
```graphql
query {
  searchUsers(filter: null, page: 0, size: 10) {
    id
    username
    email
  }
}
```

## 📝 Lưu ý
- Application cần PostgreSQL chạy tại `localhost:5432`
- Database name: `tool`
- Username: `postgres`
- Password: `123456`

Nếu chưa có database, tạo mới:
```sql
CREATE DATABASE tool;
```
