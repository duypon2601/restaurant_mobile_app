package com.example.restaurant_mobile_app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.restaurant_mobile_app.R
import com.example.restaurant_mobile_app.data.repository.MenuRepository
import com.example.restaurant_mobile_app.network.RetrofitInstance
import coil.compose.rememberAsyncImagePainter
import com.example.restaurant_mobile_app.data.model.Food
import com.example.restaurant_mobile_app.ui.FirebaseImage
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.Alignment

@Composable
fun MenuScreen(
    navController: NavHostController,
    menuId: Int = 1, // <-- Thêm tham số menuId, mặc định là 1
    cartViewModel: CartViewModel = remember { CartViewModel() },
    appViewModel: AppViewModel = remember { AppViewModel() }
) {
    val viewModel = remember { MenuViewModel(MenuRepository(RetrofitInstance.api)) }
    val menu by viewModel.menu.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val foodList = remember { mutableStateListOf<Food>() }
    val foodMap = remember { mutableStateMapOf<Int, Food>() }
    val cartCount by cartViewModel.cartItems.collectAsState()

    LaunchedEffect(menuId) {
        // Lấy danh sách food từ API
        val foods = RetrofitInstance.api.getFoods().data
        foodList.clear()
        foodList.addAll(foods)
        foodMap.clear()
        foodMap.putAll(foods.associateBy { it.id })
        viewModel.loadMenuItemsByMenuId(menuId)
    }

    when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text("Lỗi: $error")
        else -> {
            Box(modifier = Modifier.fillMaxSize()) {
                // Danh sách món ăn
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("Số lượng món: ${menu.size}", style = MaterialTheme.typography.bodyLarge)
                    LazyColumn(modifier = Modifier.weight(1f).padding(8.dp)) {
                        items(menu) { item ->
                            val name = item.foodName ?: "Không tên"
                            val imageUrl = item.imageUrl
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    FirebaseImage(
                                        storagePath = imageUrl,
                                        modifier = Modifier.size(80.dp),
                                        contentDescription = name
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(name, style = MaterialTheme.typography.titleMedium)
                                        Text("${item.price} đ", style = MaterialTheme.typography.bodyMedium)
                                    }
                                    Button(onClick = { cartViewModel.addToCart(item) }) {
                                        Text("Thêm vào giỏ")
                                    }
                                }
                            }
                        }
                    }
                }
                // Nút giỏ hàng nổi góc trên bên phải
                Box(modifier = Modifier.fillMaxSize()) {
                    FloatingActionButton(
                        onClick = { navController.navigate("cart") },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                    ) {
                        BadgedBox(
                            badge = {
                                if (cartCount.sumOf { it.quantity } > 0) {
                                    Badge { Text(cartCount.sumOf { it.quantity }.toString()) }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Xem giỏ hàng")
                        }
                    }
                }
            }
        }
    }
} 