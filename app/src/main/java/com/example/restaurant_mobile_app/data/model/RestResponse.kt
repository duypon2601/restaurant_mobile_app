package com.example.restaurant_mobile_app.data.model

data class RestResponse<T>(
    val statusCode: Int,
    val error: String?,
    val message: Any?,
    val data: T
) 