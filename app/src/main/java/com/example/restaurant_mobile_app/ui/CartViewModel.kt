package com.example.restaurant_mobile_app.ui

import androidx.lifecycle.ViewModel
import com.example.restaurant_mobile_app.data.model.CartItem
import com.example.restaurant_mobile_app.data.model.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(menuItem: MenuItem) {
        val current = _cartItems.value.toMutableList()
        val index = current.indexOfFirst { it.menuItem.id == menuItem.id }
        if (index >= 0) {
            current[index] = current[index].copy(quantity = current[index].quantity + 1)
        } else {
            current.add(CartItem(menuItem, 1))
        }
        _cartItems.value = current
    }

    fun removeFromCart(menuItemId: Int) {
        _cartItems.value = _cartItems.value.filter { it.menuItem.id != menuItemId }
    }

    fun updateQuantity(menuItemId: Int, quantity: Int) {
        _cartItems.value = _cartItems.value.map {
            if (it.menuItem.id == menuItemId) it.copy(quantity = quantity) else it
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }
} 