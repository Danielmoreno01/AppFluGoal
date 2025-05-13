package com.example.flugoal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flugoal.Model.Movimiento

/*@Composable
fun MovimientoScreen(
    movimientos: List<Movimiento> = listOf(
        Movimiento("Pago de beca", "Ingreso", 500000.0, "10 May 2025"),
        Movimiento("Suscripción Spotify", "Gasto", 16000.0, "09 May 2025"),
        Movimiento("Venta de libro", "Ingreso", 45000.0, "07 May 2025")
    )
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7FAFC))
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Movimientos 💸",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(movimientos) { movimiento ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = movimiento.descripcion,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = movimiento.fecha,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = if (movimiento.tipo == "Ingreso") "+$${movimiento.monto}" else "-$${movimiento.monto}",
                                color = if (movimiento.tipo == "Ingreso") Color(0xFF2ECC71) else Color(0xFFE74C3C),
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = movimiento.tipo,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ExtendedFloatingActionButton(
                onClick = { /* lógica para agregar movimiento */ },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Agregar Movimiento") }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovimientoScreenPreview() {
    MovimientoScreen()
}
*/