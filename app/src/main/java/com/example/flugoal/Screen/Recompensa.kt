package com.example.flugoal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Recompensa(
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val valor: Int
)

@Composable
fun RecompensaScreen(
    recompensas: List<Recompensa> = listOf(
        Recompensa("Café Gratis ☕", "Canjea por un café en tu tienda favorita", "10 May 2025", 100),
        Recompensa("Día Libre 🎉", "Tómate el día para ti", "08 May 2025", 200),
        Recompensa("Película Premium 🎬", "Renta una película online", "05 May 2025", 150)
    )
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFDFD))
            .padding(16.dp)
    ) {
        Text(
            text = "Mis Recompensas",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(recompensas) { recompensa ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = recompensa.titulo, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(text = recompensa.descripcion, fontSize = 13.sp, color = Color.DarkGray)
                            Text(text = recompensa.fecha, fontSize = 12.sp, color = Color.Gray)
                        }

                        Text(
                            text = "${recompensa.valor} pts",
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun RecompensaScreenPreview() {
    RecompensaScreen()
}
