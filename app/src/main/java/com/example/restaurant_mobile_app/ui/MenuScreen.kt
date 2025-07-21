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

@Composable
fun MenuScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = remember { CartViewModel() },
    appViewModel: AppViewModel = remember { AppViewModel() }
) {
    val viewModel = remember { MenuViewModel(MenuRepository(RetrofitInstance.api)) }
    val menu by viewModel.menu.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    val foodList = remember { mutableStateListOf<Food>() }
    val foodMap = remember { mutableStateMapOf<Int, Food>() }

    LaunchedEffect(Unit) {
        // Lấy danh sách food từ API
        val foods = RetrofitInstance.api.getFoods().data
        foodList.clear()
        foodList.addAll(foods)
        foodMap.clear()
        foodMap.putAll(foods.associateBy { it.id })
        viewModel.loadMenu()
    }

    when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text("Lỗi: $error")
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(menu) { item ->
                    val food = foodMap[item.foodId]
                    val name = food?.name ?: "Không tên"
                    val imageUrl = food?.image_url
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
            Button(
                onClick = { navController.navigate("cart") },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Xem giỏ hàng")
            }
        }
    }
} 