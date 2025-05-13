package com.example.flugoal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AvatarScreen() {
    var selectedAvatar by remember { mutableStateOf("🙂") }
    var selectedBackground by remember { mutableStateOf(Color(0xFFD0E8F2)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF7FB))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Personaliza tu Avatar",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF094067)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar Preview
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(selectedBackground, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(selectedAvatar, fontSize = 50.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Fondo (simulado)
        Text("Fondo seleccionado", color = Color(0xFF3D5A80))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            listOf(
                Color(0xFFD0E8F2),
                Color(0xFFB8DBD9),
                Color(0xFF94B3FD)
            ).forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color, shape = CircleShape)
                        .padding(4.dp)
                        .clickable { selectedBackground = color }
                )
            }
        }

        // Avatar (simulado)
        Text("Emoji de avatar", color = Color(0xFF3D5A80))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
        ) {
            listOf("🙂", "😎", "🧠", "👾", "🚀").forEach { emoji ->
                Text(
                    text = emoji,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { selectedAvatar = emoji }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Guardar avatar */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D5A80))
        ) {
            Text("Guardar Avatar", color = Color.White)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AvatarScreenPreview() {
    AvatarScreen()
}
