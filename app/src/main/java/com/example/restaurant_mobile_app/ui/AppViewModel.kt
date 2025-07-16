package com.example.restaurant_mobile_app.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppViewModel : ViewModel() {
    val selectedTableId = MutableStateFlow<Int?>(null)
} 