package com.example.flugoal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PerfilScreen(
    nombre: String = "Daniel Moreno",
    email: String = "daniel@email.com"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9F9))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.sym_def_app_icon),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
            IconButton(onClick = { /* cambiar avatar */ }) {
                Icon(Icons.Default.Edit, contentDescription = "Editar Avatar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Información
        Text(text = nombre, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(text = email, fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Divider()

        Spacer(modifier = Modifier.height(24.dp))

        // Configuraciones rápidas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Configuraciones", fontSize = 16.sp, fontWeight = FontWeight.Medium)
            IconButton(onClick = { /* Abrir configuración */ }) {
                Icon(Icons.Default.Settings, contentDescription = "Configuraciones")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Cerrar sesión */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PerfilScreenPreview() {
    PerfilScreen()
}
