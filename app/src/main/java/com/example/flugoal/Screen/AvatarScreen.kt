package com.example.flugoal.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flugoal.R
import com.example.flugoal.ViewModel.UsuarioViewModel

@Composable
fun AvatarScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val usuarioId by usuarioViewModel.usuarioId.collectAsState()
    val nombreUsuario by usuarioViewModel.usuarioNombre.collectAsState()

    val robotoFont = FontFamily(Font(R.font.concertone))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF40C045),
                        Color(0xFFF5F5F5)
                    ),
                    startY = 0f,
                    endY = 200f
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.navigateUp() }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                "Mi Perfil",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = robotoFont
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = if (nombreUsuario.isNotEmpty())
                            nombreUsuario.take(2).uppercase() else "US",
                        color = Color(0xFFD9E7DB),
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        fontFamily = robotoFont
                    )
                }
            }

            Text(
                text = if (nombreUsuario.isNotEmpty()) nombreUsuario else "Usuario",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = robotoFont,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "ID: ${usuarioId ?: "---"}",
                fontSize = 14.sp,
                color = Color.Red.copy(alpha = 0.8f),
                fontFamily = robotoFont
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                ProfileOptionCard(
                    title = "Cerrar Sesión",
                    subtitle = "Salir de tu cuenta",
                    icon = Icons.AutoMirrored.Filled.ExitToApp,
                    onClick = {
                        usuarioViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                    ,
                    isDestructive = true
                )
            }
        }
    }
}

@Composable
fun ProfileOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    isDestructive: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (isDestructive)
                            Color(0xFFFFEBEE) else Color(0xFFF3E5F5),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = if (isDestructive)
                        Color(0xFFE53E3E) else Color(0xFF6C5CE7),
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isDestructive)
                        Color(0xFFE53E3E) else Color(0xFF333333)
                )
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Ir",
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}