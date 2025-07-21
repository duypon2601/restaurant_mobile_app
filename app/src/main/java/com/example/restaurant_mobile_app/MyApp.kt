package com.example.restaurant_mobile_app

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this) // Khởi tạo FirebaseApp
        instance = this
    }

    companion object {
        private var instance: MyApp? = null
        val context get() = instance!!.applicationContext
    }
} 