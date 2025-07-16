package com.example.restaurant_mobile_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.restaurant_mobile_app.ui.theme.Restaurant_mobile_appTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurant_mobile_app.ui.TableSelectionScreen
import com.example.restaurant_mobile_app.ui.MenuScreen
import com.example.restaurant_mobile_app.ui.CartScreen
import com.example.restaurant_mobile_app.ui.OrderScreen
import com.example.restaurant_mobile_app.ui.AppViewModel
import com.example.restaurant_mobile_app.ui.CartViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Restaurant_mobile_appTheme {
                val navController = rememberNavController()
                val appViewModel = remember { AppViewModel() }
                val cartViewModel = remember { CartViewModel() }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "table_selection",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("table_selection") { TableSelectionScreen(navController, appViewModel) }
                        composable("menu") { MenuScreen(navController, cartViewModel, appViewModel) }
                        composable("cart") { CartScreen(navController, cartViewModel, appViewModel) }
                        composable("order") {
                            val tableId = appViewModel.selectedTableId.collectAsState().value
                            if (tableId != null) {
                                OrderScreen(navController, cartViewModel, tableId as Int)
                            } else {
                                Text("Vui lòng chọn bàn trước!")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Restaurant_mobile_appTheme {
        Greeting("Android")
    }
}