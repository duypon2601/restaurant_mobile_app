package com.example.restaurant_mobile_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.restaurant_mobile_app.data.model.Order
import com.example.restaurant_mobile_app.data.model.OrderItem
import com.example.restaurant_mobile_app.data.repository.OrderRepository
import com.example.restaurant_mobile_app.network.RetrofitInstance
import java.math.BigDecimal

@Composable
fun OrderScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel = remember { CartViewModel() },
    tableId: Int // Bắt buộc truyền đúng bàn đã chọn
) {
    val orderViewModel = remember { OrderViewModel(OrderRepository(RetrofitInstance.api)) }
    val isLoading by orderViewModel.isLoading.collectAsState()
    val success by orderViewModel.success.collectAsState()
    val error by orderViewModel.error.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()

    var sent by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Xác nhận đơn hàng", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        if (!sent) {
            Button(
                onClick = {
                    val orderItems = cartItems.map {
                        OrderItem(
                            menuItemId = it.menuItem.id,
                            quantity = it.quantity,
                            price = it.menuItem.price, // Đã là Double, không cần toBigDecimal()
                            menuItemName = it.menuItem.name ?: "Không tên"
                        )
                    }
                    val totalPrice = cartItems.fold(0.0) { acc, item ->
                        acc + (item.menuItem.price * item.quantity)
                    }
                    val order = Order(
                        diningTableId = tableId,
                        orderItems = orderItems,
                        totalPrice = totalPrice // Đã là Double
                    )
                    orderViewModel.sendOrder(order)
                    sent = true
                    cartViewModel.clearCart()
                },
                enabled = cartItems.isNotEmpty() && !isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gửi đơn hàng")
            }
        }
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Lỗi: $error", color = MaterialTheme.colorScheme.error)
            success == true -> Text("Gửi đơn thành công!", color = MaterialTheme.colorScheme.primary)
            success == false && sent -> Text("Gửi đơn thất bại!", color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate("table_selection") }, modifier = Modifier.fillMaxWidth()) {
            Text("Quay lại chọn bàn")
        }
    }
} 