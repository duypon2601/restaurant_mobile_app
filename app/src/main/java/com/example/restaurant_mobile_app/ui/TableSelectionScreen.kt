package com.example.restaurant_mobile_app.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.restaurant_mobile_app.data.repository.TableRepository
import com.example.restaurant_mobile_app.network.RetrofitInstance

@Composable
fun TableSelectionScreen(
    navController: NavHostController,
    appViewModel: AppViewModel = remember { AppViewModel() }
) {
    val viewModel = remember { TableSelectionViewModel(TableRepository(RetrofitInstance.api)) }
    val tables by viewModel.tables.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadTables() }

    when {
        isLoading -> CircularProgressIndicator()
        error != null -> Text("Lỗi: ${error ?: "Không xác định"}")
        tables.isEmpty() -> Text("Không có bàn nào hoặc lỗi dữ liệu!")
        else -> LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(tables) { table ->
                Button(
                    onClick = {
                        appViewModel.selectedTableId.value = table.id
                        navController.navigate("menu")
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Text("${table.qrCode ?: "Bàn ${table.id}"} (${table.status})")
                }
            }
        }
    }
} 