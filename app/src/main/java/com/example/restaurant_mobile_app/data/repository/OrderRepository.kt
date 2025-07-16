package com.example.restaurant_mobile_app.data.repository

import com.example.restaurant_mobile_app.data.model.Order
import com.example.restaurant_mobile_app.data.model.RestResponse
import com.example.restaurant_mobile_app.network.ApiService
 
class OrderRepository(private val api: ApiService) {
    suspend fun sendOrder(order: Order): Boolean = api.sendOrder(order).statusCode == 201
} 