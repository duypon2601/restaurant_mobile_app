package com.example.restaurant_mobile_app.data.repository

import com.example.restaurant_mobile_app.data.model.Table
import com.example.restaurant_mobile_app.data.model.RestResponse
import com.example.restaurant_mobile_app.network.ApiService

class TableRepository(private val api: ApiService) {
    suspend fun getTables(): List<Table> = api.getTables().data
} 