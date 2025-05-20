package com.example.flugoal.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.flugoal.Model.Movimiento

@Composable
fun HistorialMovimientosScreen(movimientos: List<Movimiento>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Historial de Movimientos",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF3949AB),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movimientos.size) { index ->
                MovimientoCard(movimiento = movimientos[index])
            }
        }
    }
}

@Composable
fun MovimientoCard(movimiento: Movimiento) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = movimiento.tipo, fontWeight = FontWeight.Bold)
            Text(text = "Monto: $${movimiento.monto}")
            Text(text = "Fecha: ${movimiento.fecha}")
            movimiento.descripcion?.let {
                Text(text = "Descripción: $it")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistorialMovimientos() {
    HistorialMovimientosScreen(movimientos = emptyList())
}
