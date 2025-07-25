package com.example.restaurant_mobile_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.restaurant_mobile_app.data.model.Food
import com.example.restaurant_mobile_app.network.RetrofitInstance

@Composable
fun CartScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = remember { CartViewModel() },
    appViewModel: AppViewModel = remember { AppViewModel() }
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val foodList = remember { mutableStateListOf<Food>() }
    val foodMap = remember { mutableStateMapOf<Int, Food>() }

    LaunchedEffect(Unit) {
        val foods = RetrofitInstance.api.getFoods().data
        foodList.clear()
        foodList.addAll(foods)
        foodMap.clear()
        foodMap.putAll(foods.associateBy { it.id })
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Text("Giỏ hàng", style = MaterialTheme.typography.titleLarge)
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cartItems) { cartItem ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        val food = foodMap[cartItem.menuItem.foodId]
                        val name = food?.name ?: "Không tên"
                        Text(name)
                        Text("${cartItem.menuItem.price} đ")
                    }
                    Row {
                        IconButton(onClick = {
                            val newQty = cartItem.quantity - 1
                            if (newQty > 0) cartViewModel.updateQuantity(cartItem.menuItem.id, newQty)
                            else cartViewModel.removeFromCart(cartItem.menuItem.id)
                        }) {
                            Text("-")
                        }
                        Text(cartItem.quantity.toString(), modifier = Modifier.padding(horizontal = 8.dp))
                        IconButton(onClick = {
                            cartViewModel.updateQuantity(cartItem.menuItem.id, cartItem.quantity + 1)
                        }) {
                            Text("+")
                        }
                        IconButton(onClick = {
                            cartViewModel.removeFromCart(cartItem.menuItem.id)
                        }) {
                            Text("X")
                        }
                    }
                }
            }
        }
        Button(
            onClick = { navController.navigate("order") },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            enabled = cartItems.isNotEmpty()
        ) {
            Text("Gửi đơn hàng")
        }
    }
} 