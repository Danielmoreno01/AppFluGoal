package com.example.flugoal.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?,
) {
    var expanded by remember { mutableStateOf(false) }

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

        Box(
            modifier = Modifier.wrapContentSize(Alignment.TopCenter)
        ) {
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .size(56.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Nuevo Movimiento",
                    tint = Color.Black
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.Black),
                offset = DpOffset(x = (-80).dp, y = 0.dp)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            "Crear nuevo movimiento",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                    },
                    onClick = {
                        navController.navigate("nuevo_movimiento")
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            "Crear nueva meta",
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                    },
                    onClick = {
                        navController.navigate("nueva_meta")
                        expanded = false
                    }
                )
            }
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
