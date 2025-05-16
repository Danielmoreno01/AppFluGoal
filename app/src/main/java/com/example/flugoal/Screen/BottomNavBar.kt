package com.example.flugoal.Screen

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?,
    onCreateClick: () -> Unit
) {
    NavigationBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Tienda") },
            selected = currentRoute == "movimientos",
            onClick = { navController.navigate("movimientos") }
        )

        // Botón central +
        FloatingActionButton(
            onClick = { navController.navigate("nuevo_movimiento") },
            containerColor = Color.White,
            contentColor = Color.Black,
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Crear")
        }


        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Gastos") },
            selected = currentRoute == "gastos",
            onClick = { navController.navigate("gastos") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
            selected = currentRoute == "perfil",
            onClick = { navController.navigate("perfil") }
        )
    }
}
