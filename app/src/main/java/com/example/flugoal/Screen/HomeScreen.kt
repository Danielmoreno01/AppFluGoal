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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    val robotoFont = FontFamily(Font(R.font.concertone)) // Usa la misma fuente que en NuevoMovimientoScreen

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
        MenuItem("Ver Metas", Icons.Default.Star, "lista_metas"), // *
        MenuItem("Historial de Movimientos", Icons.AutoMirrored.Filled.ExitToApp, "lista_movimientos"),
        MenuItem("Egresos", Icons.Default.List, "lista_egresos"),
        MenuItem("Ingresos", Icons.Default.KeyboardArrowRight, "lista_ingresos"),
        MenuItem("Ingresos a Metas", Icons.Default.Favorite, "ingresos_metas"),
        MenuItem("Dinero Ahorrado", Icons.Default.Star, "dinero_ahorrado"),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "Finanzas Personales",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF080C23),
                fontFamily = robotoFont
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (nombreUsuario.isNotEmpty()) nombreUsuario else "?",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontFamily = robotoFont
                )
                Spacer(modifier = Modifier.width(8.dp))
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF000000)),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = if (nombreUsuario.isNotEmpty()) nombreUsuario.first().toString().uppercase() else "?",
                            color = Color(0xFF58D5E5),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = robotoFont
                        )
                    }
                }
            }
        }

        Divider(
            color = Color(0xFFBDBDBD),
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
                    tint = Color(0xFF000000),
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
