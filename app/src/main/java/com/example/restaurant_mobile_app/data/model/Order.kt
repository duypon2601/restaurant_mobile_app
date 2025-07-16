package com.example.restaurant_mobile_app.data.model

// Đổi BigDecimal thành Double, thêm status mặc định cho Order

data class Order(
    val diningTableId: Int,
    val orderItems: List<OrderItem>,
    val totalPrice: Double, // Đổi BigDecimal thành Double
    val status: String = "PENDING" // Thêm trường status mặc định
)

data class OrderItem(
    val menuItemId: Int,
    val quantity: Int,
    val price: Double, // Đổi BigDecimal thành Double
    val menuItemName: String
) 