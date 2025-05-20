package com.example.flugoal.Screen

import androidx.compose.ui.tooling.preview.Preview
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController

data class Movimiento(
    val descripcion: String,
    val monto: Double,
    val fecha: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialMovimientosScreen(
    navController: NavHostController?,
    movimientos: List<Movimiento> = emptyList()
) {
    val robotoFont = FontFamily(Font(R.font.concertone))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {
        Text(
            text = "Historial de Movimientos",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF080C23),
            fontFamily = robotoFont,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(movimientos) { movimiento ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = movimiento.descripcion,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF080C23),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Monto: $${movimiento.monto}",
                            color = if (movimiento.monto >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                        )
                        Text(
                            text = "Fecha: ${movimiento.fecha}",
                            color = Color.DarkGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewHistorialMovimientosScreen() {
    HistorialMovimientosScreen(navController = null, movimientos = emptyList())
}
