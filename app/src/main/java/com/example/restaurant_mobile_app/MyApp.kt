package com.example.restaurant_mobile_app

import android.app.Application

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MyApp? = null
        val context get() = instance!!.applicationContext
    }
} 