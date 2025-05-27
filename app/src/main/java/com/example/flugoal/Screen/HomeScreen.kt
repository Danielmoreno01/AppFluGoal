package com.example.flugoal.Screen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.flugoal.Model.MenuItem
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel

@Composable
fun HomeScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val usuarioId by usuarioViewModel.usuarioId.collectAsState()
    val nombreUsuario by usuarioViewModel.usuarioNombre.collectAsState()

    LaunchedEffect(usuarioId) {
        usuarioId?.toLongOrNull()?.let {
            usuarioViewModel.cargarNombreUsuarioPorId(it)
        }
    }

    val robotoFont = FontFamily(Font(R.font.concertone))

    val cardColors = listOf(
        Color(0xFFABD7B9),
        Color(0xFFE1BEE7),
        Color(0xFFD1C4E9),
        Color(0xFFC5CAE9),
        Color(0xFFBBDEFB),
        Color(0xFFB3E5FC),
        Color(0xFFB2EBF2),
        Color(0xFFB2DFDB),
        Color(0xFFC8E6C9),
        Color(0xFFDCEDC8)
    )

    val menuItems = listOf(
        MenuItem("Informacion General", Icons.Default.Info, "info"),
        MenuItem("Historial de Movimientos", Icons.AutoMirrored.Filled.ExitToApp, "lista_movimientos"),
        MenuItem("Ver Metas", Icons.Default.Star, "lista_metas"),
        MenuItem("Egresos", Icons.Default.List, "lista_egresos"),
        MenuItem("Ingresos", Icons.Default.KeyboardArrowRight, "lista_ingresos"),
        MenuItem("Ingresos a Metas", Icons.Default.Favorite, "lista_ingresos_metas"),
        MenuItem("Dinero Ahorrado", Icons.Default.Star, "lista_ahorros"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF5B8A23),
                        Color(0xFFF5F5F5)
                    ),
                    startY = 0f,
                    endY = 200f
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Finanzas Personales",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = robotoFont
                )

            }

            Text(
                text = "Hola, ${if (nombreUsuario.isNotEmpty()) nombreUsuario else "Usuario"}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = robotoFont,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "¿Qué quieres hacer hoy?",
                fontSize = 16.sp,
                color = Color.Black.copy(alpha = 0.8f),
                fontFamily = robotoFont,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardCard(
                    title = "Balance Total",
                    value = "$12,450",
                    icon = Icons.Default.Check,
                    modifier = Modifier.weight(1f)
                )

                DashboardCard(
                    title = "Este Mes",
                    value = "+$2,300",
                    icon = Icons.Default.KeyboardArrowUp,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            itemsIndexed(menuItems) { index, item ->
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
fun DashboardCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = title,
                tint = Color(0xFF04838F),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = Color.Black.copy(alpha = 0.8f),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = value,
                    fontSize = 16.sp,
                    color = Color(0xFF071885),
                    fontWeight = FontWeight.Bold
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
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
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
                    tint = Color(0xFF000000),
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                title,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFF333333),
                lineHeight = 16.sp
            )
        }
    }
}