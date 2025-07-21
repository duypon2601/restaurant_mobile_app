package com.example.restaurant_mobile_app.network

import com.example.restaurant_mobile_app.data.model.*
import com.example.restaurant_mobile_app.data.model.RestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("dining-table")
    suspend fun getTables(): RestResponse<List<Table>>

    @GET("menu")
    suspend fun getMenu(): RestResponse<List<MenuItem>>

    @GET("food")
    suspend fun getFoods(): RestResponse<List<Food>>

    @POST("order/create")
    suspend fun sendOrder(@Body order: Order): RestResponse<Any>
} 