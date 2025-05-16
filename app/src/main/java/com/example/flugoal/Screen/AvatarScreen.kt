package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
@Composable
fun AvatarScreen(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = currentRoute,
                onCreateClick = { navController.navigate("nuevo_movimiento") }
            )
        },
        containerColor = Color(0xFF121212) // fondo oscuro elegante para Scaffold
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // para evitar que el contenido quede bajo la barra
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = "https://cdn-icons-png.flaticon.com/512/565/565547.png",
                contentDescription = "Imagen en construcción",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 32.dp)
            )

            Text(
                text = "Pantalla en Construcción",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Estamos trabajando para traerte esta funcionalidad muy pronto.",
                color = Color.LightGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { /* acción de volver o navegar */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
            ) {
                Text(text = "Volver", color = Color.White)
            }
        }
    }
}
