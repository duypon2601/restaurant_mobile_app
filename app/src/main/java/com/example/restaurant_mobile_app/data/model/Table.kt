package com.example.restaurant_mobile_app.data.model
 
data class Table(
    val id: Int,
    val qrCode: String?,
    val status: String, // hoặc DiningTableStatus nếu muốn dùng enum
    val restaurantId: Int,
    val isDeleted: Boolean
) 