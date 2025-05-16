package com.example.flugoal.Screen

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.R
import kotlinx.coroutines.launch

@Composable
fun MovimientosScreen(navController: NavController) {
    val movimientos = remember { mutableStateListOf<Movimiento>() }
    val scope = rememberCoroutineScope()
    val robotoFont = FontFamily(Font(R.font.concertone))

    // Estado para la selección de tipo visual
    var selectedTipo by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = RetrofitClient.apiService.obtenerMovimientos()
            movimientos.clear()
            movimientos.addAll(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondomovimiento),
            contentDescription = "Fondo desenfocado tipo banco",
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.graphicsLayer {
                            renderEffect = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
                                .asComposeRenderEffect()
                        }
                    } else Modifier
                ),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("nuevo_movimiento") },
                    containerColor = Color.White.copy(alpha = 0.2f),
                    contentColor = Color.White,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    modifier = Modifier
                        .size(56.dp)
                        .offset(y = (-8).dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar")
                }
            },
            bottomBar = {
                BottomNavBar(
                    navController = navController,
                    currentRoute = navController.currentBackStackEntry?.destination?.route,
                    onCreateClick = { navController.navigate("nuevo_movimiento") }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Historial de Movimientos",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        fontFamily = robotoFont,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Selector visual para tipo de movimiento
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    //  ingreso
                    IconToggleButton(
                        checked = selectedTipo == "ingreso",
                        onCheckedChange = { isChecked ->
                            selectedTipo = if (isChecked) "ingreso" else null
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = if (selectedTipo == "ingreso") R.drawable.fondomovimiento else R.drawable.fondomovimiento),
                            contentDescription = "Ingreso",
                            tint = if (selectedTipo == "ingreso") Color(0xFF4CAF50) else Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    // Cara egreso
                    IconToggleButton(
                        checked = selectedTipo == "egreso",
                        onCheckedChange = { isChecked ->
                            selectedTipo = if (isChecked) "egreso" else null
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = if (selectedTipo == "egreso") R.drawable.fondomovimiento else R.drawable.fondomovimiento),
                            contentDescription = "Egreso",
                            tint = if (selectedTipo == "egreso") Color(0xFFF44336) else Color.Gray,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    // Filtrar movimientos si hay filtro activo
                    val filteredMovimientos = if (selectedTipo == null) {
                        movimientos
                    } else {
                        movimientos.filter { it.tipo == selectedTipo }
                    }

                    items(filteredMovimientos) { movimiento ->
                        val cardColor = if (movimiento.tipo == "ingreso") Color(0xFFC3EACE) else Color(0xFFFFCDD2)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = cardColor
                            ),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Mostrar nombre o título del movimiento
                                Text(
                                    text = movimiento.descripcion ?: "Movimiento sin nombre",
                                    color = Color.Black,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Text(
                                    text = "Tipo: ${movimiento.tipo.capitalize()}",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Cantidad: ${movimiento.monto}",
                                    color = Color.Black
                                )
                                Text(
                                    text = "Fecha: ${movimiento.fecha ?: "No disponible"}",
                                    color = Color.DarkGray,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Descripción: ${movimiento.descripcion ?: "Sin descripción"}",
                                    color = Color.Black,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
