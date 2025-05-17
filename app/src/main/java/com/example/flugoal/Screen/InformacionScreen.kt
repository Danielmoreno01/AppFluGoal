package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

data class Tarea(val titulo: String, val descripcion: String)

@Composable
fun InformacionScreen(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val tareas = listOf(
        Tarea("Matemáticas", "Resolver ejercicios de la guía."),
        Tarea("Física", "Realizar experimento de péndulo."),
        Tarea("Inglés", "Completar workbook unidad 3.")
    )

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = currentRoute,
                onCreateClick = { navController.navigate("nuevo_movimiento") }
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Tareas Pendientes",
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn {
                items(tareas) { tarea ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F1F1F))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = tarea.titulo,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = tarea.descripcion,
                                fontSize = 14.sp,
                                color = Color.LightGray
                            )
                        }
                    }
                }
            }
        }
    }
}
