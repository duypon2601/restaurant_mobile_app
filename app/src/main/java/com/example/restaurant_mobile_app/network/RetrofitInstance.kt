package com.example.restaurant_mobile_app.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import com.example.restaurant_mobile_app.MyApp

object RetrofitInstance {
    // TODO: Thay đổi BASE_URL thành địa chỉ backend của bạn
    private const val BASE_URL = "http://10.0.2.2:8080/" // Localhost cho Android emulator

    // Hàm lấy token từ SharedPreferences
    private fun getToken(): String? {
        return MyApp.context.getSharedPreferences("app_prefs", 0)
            .getString("firebase_token", null)
    }

    private val authInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        getToken()?.let { token ->
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        chain.proceed(requestBuilder.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
} 