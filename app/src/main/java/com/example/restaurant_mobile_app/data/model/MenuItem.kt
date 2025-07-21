package com.example.restaurant_mobile_app.data.model

data class MenuItem(
    val id: Int,
    val price: Double,
    val foodId: Int,
    val restaurantMenuId: Int,
    val isAvailable: Boolean
) 