package com.example.flugoal.Screen


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.flugoal.R

data class Egreso(val descripcion: String, val monto: Double, val fecha: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialEgresosScreen(navController: NavHostController?) {
    val robotoFont = FontFamily(Font(R.font.concertone))

    val egresos = remember {
        listOf(
            Egreso("Pago de servicios", -300000.0, "2025-05-12"),
            Egreso("Compra mercado", -450000.0, "2025-05-13"),
            Egreso("Pago transporte", -120000.0, "2025-05-15"),
            Egreso("Ropa", -250000.0, "2025-05-16")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp)
    ) {
        Text(
            text = "Historial de Egresos",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF080C23),
            fontFamily = robotoFont,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(egresos) { egreso ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = egreso.descripcion,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF080C23),
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Monto: $${egreso.monto}",
                            color = Color(0xFFF44336) // rojo
                        )
                        Text(
                            text = "Fecha: ${egreso.fecha}",
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
fun PreviewHistorialEgresosScreen() {
    HistorialEgresosScreen(navController = null)
}
