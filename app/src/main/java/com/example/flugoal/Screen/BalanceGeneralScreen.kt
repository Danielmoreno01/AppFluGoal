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
import com.example.flugoal.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceGeneralScreen(navController: NavHostController?) {
    val robotoFont = FontFamily(Font(R.font.concertone))

    val movimientos = remember {
        listOf(
            Movimiento("Sueldo mensual", 1500000.0, "2025-05-10"),
            Movimiento("Venta artículos", 300000.0, "2025-05-11"),
            Movimiento("Renta", -500000.0, "2025-05-12"),
            Movimiento("Reembolso", 200000.0, "2025-05-13"),
            Movimiento("Servicios", -250000.0, "2025-05-14"),
            Movimiento("Ingresos extras", 450000.0, "2025-05-15"),
            Movimiento("Comida", -200000.0, "2025-05-16")
        )
    }

    val total = movimientos.sumOf { it.monto }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {
        Text(
            text = "Balance General",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF080C23),
            fontFamily = robotoFont,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(movimientos) { mov ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = mov.descripcion,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF080C23),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Monto: $${mov.monto}",
                            color = if (mov.monto >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
                        )
                        Text(
                            text = "Fecha: ${mov.fecha}",
                            color = Color.DarkGray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEEEEEE)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Balance Total: $${total}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (total >= 0) Color(0xFF4CAF50) else Color(0xFFF44336),
                    fontFamily = robotoFont
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewBalanceGeneralScreen() {
    BalanceGeneralScreen(navController = null)
}
