package com.example.restaurant_mobile_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.restaurant_mobile_app.data.repository.MenuRepository
import com.example.restaurant_mobile_app.network.RetrofitInstance

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

    LaunchedEffect(Unit) { viewModel.loadMenu() }

    when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text("Lỗi: $error")
        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(menu) { item ->
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
                            Column {
                                Text(item.name ?: "Không tên", style = MaterialTheme.typography.titleMedium)
                                Text(item.description ?: "", style = MaterialTheme.typography.bodySmall)
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