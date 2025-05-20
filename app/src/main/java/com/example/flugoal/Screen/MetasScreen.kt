package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flugoal.Model.Meta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetasScreen(navController: NavHostController) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = currentRoute,
                onCreateClick = { /* ya estamos aquí */ }
            )
        },
        containerColor = Color(0xFF121212)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val nombreState = remember { mutableStateOf("") }
            val montoState = remember { mutableStateOf("") }
            val fechaInicio = remember {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            }
            val fechaFinState = remember { mutableStateOf("") }

            Text(
                text = "Crear Nueva Meta",
                fontSize = 28.sp,
                color = Color(0xFF81D4FA), // Azul claro vibrante
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = nombreState.value,
                onValueChange = { nombreState.value = it },
                label = { Text("Nombre de la Meta", color = Color(0xFF90CAF9)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF1E1E2F), // fondo oscuro
                    focusedIndicatorColor = Color(0xFF81D4FA),
                    unfocusedIndicatorColor = Color(0xFF828A8D),
                    focusedLabelColor = Color(0xFF81D4FA),
                    unfocusedLabelColor = Color(0xFF90CAF9),
                    focusedTextColor = Color.White,    // texto blanco
                    unfocusedTextColor = Color.White,  // texto blanco
                    cursorColor = Color(0xFF81D4FA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = montoState.value,
                onValueChange = { montoState.value = it },
                label = { Text("Monto Total", color = Color(0xFF90CAF9)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF1E1E2F),
                    focusedIndicatorColor = Color(0xFF81D4FA),
                    unfocusedIndicatorColor = Color(0xFF90999D),
                    focusedLabelColor = Color(0xFF81D4FA),
                    unfocusedLabelColor = Color(0xFF90CAF9),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF81D4FA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = fechaInicio,
                onValueChange = {},
                label = { Text("Fecha de Inicio", color = Color(0xFF90CAF9)) },
                enabled = false,
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color(0xFFB0BEC5), // gris claro para texto deshabilitado
                    disabledLabelColor = Color(0xFF90CAF9),
                    containerColor = Color(0xFF1E1E2F)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = fechaFinState.value,
                onValueChange = { fechaFinState.value = it },
                label = { Text("Fecha de Fin (yyyy-MM-dd)", color = Color(0xFF90CAF9)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xFF1E1E2F),
                    focusedIndicatorColor = Color(0xFF81D4FA),
                    unfocusedIndicatorColor = Color(0xFF37474F),
                    focusedLabelColor = Color(0xFF81D4FA),
                    unfocusedLabelColor = Color(0xFF90CAF9),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFF81D4FA)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val nuevaMeta = Meta(
                                nombre = nombreState.value,
                                montoTotal = montoState.value.toDoubleOrNull() ?: 0.0,
                                fechaInicio = fechaInicio,
                                fechaFin = fechaFinState.value,
                                usuario = null,
                                movimientos = null
                            )
                            // Aquí iría tu llamada a Retrofit para guardar la meta
                            // RetrofitClient.apiService.guardarMeta(nuevaMeta)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Guardar", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Meta", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
