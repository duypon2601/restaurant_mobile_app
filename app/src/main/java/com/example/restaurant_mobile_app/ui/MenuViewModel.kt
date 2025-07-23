package com.example.restaurant_mobile_app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurant_mobile_app.data.model.MenuItem
import com.example.restaurant_mobile_app.data.repository.MenuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel(private val repository: MenuRepository) : ViewModel() {
    private val _menu = MutableStateFlow<List<MenuItem>>(emptyList())
    val menu: StateFlow<List<MenuItem>> = _menu

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadMenu() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _menu.value = repository.getMenu()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadMenuItemsByMenuId(menuId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _menu.value = repository.getMenuItemsByMenuId(menuId)
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
} 