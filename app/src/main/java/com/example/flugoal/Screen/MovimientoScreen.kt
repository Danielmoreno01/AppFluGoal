package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MovimientoScreen() {
    val movimientos = listOf("Inicio sesión", "Completó tarea", "Reclamó recompensa")

    Column(modifier = Modifier.padding(24.dp)) {
        Text("Historial de Movimientos", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        movimientos.forEach {
            Text("• $it", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MovimientoScreenPreview() {
    MovimientoScreen()
}
