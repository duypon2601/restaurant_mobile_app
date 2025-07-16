package com.example.restaurant_mobile_app.data.repository

import com.example.restaurant_mobile_app.data.model.MenuItem
import com.example.restaurant_mobile_app.data.model.RestResponse
import com.example.restaurant_mobile_app.network.ApiService
 
class MenuRepository(private val api: ApiService) {
    suspend fun getMenu(): List<MenuItem> = api.getMenu().data
} 