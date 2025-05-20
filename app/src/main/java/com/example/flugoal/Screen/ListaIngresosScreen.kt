package com.example.flugoal.Screen

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
fun ListaIngresosScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
    val usuarioId = usuarioViewModel.usuarioId.collectAsState().value
    val movimientoViewModel: MovimientoViewModel = viewModel()
    val ingresosMovimientos by movimientoViewModel.ingresosMovimientos.collectAsState()
    val robotoFont = FontFamily(Font(R.font.concertone))
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFE3F2FD),
        targetValue = Color(0xFFF5F5F5),
        animationSpec = infiniteRepeatable(tween(4000, easing = LinearEasing), RepeatMode.Reverse)
    )

    LaunchedEffect(usuarioId) {
        usuarioId?.let { movimientoViewModel.cargarIngresosPorUsuario(it) }
    }

    Scaffold(
        containerColor = animatedColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Ingresos",
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
            LazyColumn {
                items(ingresosMovimientos) { movimiento ->
                    IngresoItemStyled(
                        movimiento = movimiento,
                        fontFamily = robotoFont,
                        onEdit = {
                            movimiento.id?.let { navController.navigate("editar_ingreso/$it") }
                        },
                        onDelete = {
                            movimiento.id?.let {
                                movimientoViewModel.eliminarMovimiento(it.toInt(),
                                    onSuccess = {
                                        usuarioId?.let { id ->
                                            movimientoViewModel.cargarIngresosPorUsuario(id.toString())
                                        }
                                    },
                                    onError = { errorMsg -> Log.e("ListaIngresosScreen", "Error: $errorMsg") }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun IngresoItemStyled(
    movimiento: Movimiento,
    fontFamily: FontFamily,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    movimiento.tipo,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily,
                    fontSize = 18.sp
                )
                Text(
                    text = "Monto: ${
                        try {
                            "$" + String.format("%.2f", movimiento.monto ?: 0.0)
                        } catch (e: Exception) {
                            "$0.00"
                        }
                    }",
                    fontFamily = fontFamily,
                    color = Color(0xFF43A047) // Verde para ingresos
                )
                Text(
                    "Fecha: ${movimiento.fecha}",
                    fontFamily = fontFamily,
                    fontSize = 12.sp
                )
                Text(
                    "Descripción: ${movimiento.descripcion ?: ""}",
                    fontFamily = fontFamily,
                    fontSize = 12.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}
