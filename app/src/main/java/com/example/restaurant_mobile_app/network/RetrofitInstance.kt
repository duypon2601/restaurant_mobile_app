package com.example.restaurant_mobile_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // TODO: Thay đổi BASE_URL thành địa chỉ backend của bạn
    private const val BASE_URL = "http://10.0.2.2:8080/" // Localhost cho Android emulator

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
} 