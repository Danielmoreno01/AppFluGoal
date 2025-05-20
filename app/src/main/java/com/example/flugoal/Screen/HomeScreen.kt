package com.example.flugoal.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.flugoal.LoginScreen
import com.example.flugoal.Model.MenuItem

@Composable
fun HomeScreen(navController: NavController, userName: String = "._. User") {
    // Definición de colores para las tarjetas (colores claros variados)
    val cardColors = listOf(
        Color(0xFFABD7B9), // Rosa claro
        Color(0xFFE1BEE7), // Púrpura claro
        Color(0xFFD1C4E9), // Violeta claro
        Color(0xFFC5CAE9), // Índigo claro
        Color(0xFFBBDEFB), // Azul claro
        Color(0xFFB3E5FC), // Azul claro 2
        Color(0xFFB2EBF2), // Cian claro
        Color(0xFFB2DFDB), // Verde-azulado claro
        Color(0xFFC8E6C9), // Verde claro
        Color(0xFFDCEDC8)  // Verde lima claro
    )

    val menuItems = listOf(
        MenuItem("Movimientos", Icons.AutoMirrored.Filled.ExitToApp, "movimientos"),
        MenuItem("Metas", Icons.Default.Star, "metas"),
        MenuItem("Metas de Usuario", Icons.Default.Info, "listar_metas_usuario"),
        MenuItem("Tareas", Icons.Default.CheckCircle, "tareas"),
        MenuItem("Avatares", Icons.Default.AccountCircle, "avatares"),
        MenuItem("Tienda", Icons.Default.ShoppingCart, "tienda"),
        MenuItem("Perfil", Icons.Default.Person, "perfil")
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo gris muy claro en lugar de verde
            .padding(16.dp)
    ) {
        // Header con título y usuario
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título principal
            Text(
                "Finanzas Personales",  // Cambio de "Mi Finanzas" a "Finanzas Personales"
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3949AB) // Azul índigo en lugar de verde
            )

            // Perfil de usuario
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = userName,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = Color(0xFF000000) // Azul índigo en lugar de verde
                )
                Spacer(modifier = Modifier.width(8.dp))
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF000000)), // Índigo
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = userName.first().toString().uppercase(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }

        // Línea divisoria
        Divider(
            color = Color(0xFFBDBDBD), // Gris en lugar de verde
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(menuItems) { index, item ->
                // Usar color diferente para cada tarjeta basado en el índice
                MenuCard(
                    title = item.title,
                    icon = item.icon,
                    route = item.route,
                    navController = navController,
                    backgroundColor = cardColors[index % cardColors.size]
                )
            }
        }
    }
}

@Composable
fun MenuCard(
    title: String,
    icon: ImageVector,
    route: String,
    navController: NavController,
    backgroundColor: Color = Color.White
) {
    Card(
        onClick = { navController.navigate(route) },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
                    .padding(8.dp)
            ) {
                Icon(
                    icon,
                    contentDescription = title,
                    tint = Color(0xFF000000), // Azul índigo en lugar de verde
                    modifier = Modifier.size(32.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                title,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF333333)
            )
        }
    }
}

data class MenuItem(val title: String, val icon: ImageVector, val route: String)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}