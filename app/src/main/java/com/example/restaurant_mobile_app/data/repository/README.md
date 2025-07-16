# repository/

Tạo các repository để gọi API và xử lý dữ liệu cho Table, Menu, Cart, Order...

Ví dụ:
```kotlin
class TableRepository(private val api: ApiService) {
    suspend fun getTables() = api.getTables()
}
``` 