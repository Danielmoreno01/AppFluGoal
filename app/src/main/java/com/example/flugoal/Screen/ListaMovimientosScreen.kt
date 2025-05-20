package com.example.flugoal.Screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MovimientoViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaMovimientosScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    movimientoViewModel: MovimientoViewModel = viewModel()
) {
    val usuarioId = usuarioViewModel.usuarioId.collectAsState().value
    val movimientos by movimientoViewModel.historialMovimientos.collectAsState()
    val robotoFont = FontFamily(Font(R.font.concertone))
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFE3F2FD),
        targetValue = Color(0xFFF5F5F5),
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Reverse)
    )

    // Carga el historial solo si usuarioId NO es nulo
    LaunchedEffect(usuarioId) {
        usuarioId?.let {
            movimientoViewModel.cargarHistorialMovimientos(it.toString())
        }
    }

    Scaffold(
        containerColor = animatedColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Movimientos",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF080C23),
                        fontFamily = robotoFont,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = animatedColor)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = "Volver al Home")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(animatedColor)
        ) {
            if (movimientos.isEmpty()) {
                // Puedes mostrar un texto o animación cuando no hay movimientos
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No hay movimientos registrados",
                        fontFamily = robotoFont,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            } else {
                LazyColumn {
                    items(movimientos) { movimiento ->
                        MovimientoItemStyled(movimiento = movimiento, fontFamily = robotoFont)
                    }
                }
            }
        }
    }
}


@Composable
fun MovimientoItemStyled(movimiento: Movimiento, fontFamily: FontFamily) {
    val colorTipo = when (movimiento.tipo) {
        "Ingreso" -> Color(0xFF2E7D32)
        "Egreso" -> Color(0xFFC62828)
        "Ahorro" -> Color(0xFF1565C0)
        "IngresoMeta" -> Color(0xFF00897B)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movimiento.descripcion ?: "Sin descripción",
                fontWeight = FontWeight.Bold,
                fontFamily = fontFamily,
                fontSize = 18.sp
            )
            Text(
                text = "Monto: \$${String.format("%.2f", movimiento.monto)}",
                fontFamily = fontFamily,
                color = colorTipo
            )
            Text(
                text = "Tipo: ${movimiento.tipo}",
                fontFamily = fontFamily,
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
            Text(
                text = "Fecha: ${movimiento.fecha}",
                fontFamily = fontFamily,
                fontSize = 12.sp,
                color = Color.DarkGray
            )
            movimiento.presupuesto?.let {
                Text(
                    text = "Meta: ${it.nombre}",
                    fontFamily = fontFamily,
                    fontSize = 13.sp,
                    color = Color(0xFF5D4037)
                )
            }
        }
    }
}
