package com.example.flugoal.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.flugoal.Model.Movimiento
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoMovimientoScreen(navController: NavHostController) {
    val opciones = listOf("Ingreso", "Egreso", "Ahorro")

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = navController.currentBackStackEntry?.destination?.route,
                onCreateClick = { navController.navigate("nuevo_movimiento") }
            )
        },
        containerColor = Color(0xFF0D0F2C)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val tipoState = remember { mutableStateOf("") }
            val expandedState = remember { mutableStateOf(false) }
            val montoState = remember { mutableStateOf("") }
            val descripcionState = remember { mutableStateOf("") }
            val metaState = remember { mutableStateOf("") }

            val fechaActual = remember {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            }

            Text(
                text = "Nuevo Movimiento",
                fontSize = 28.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = fechaActual,
                onValueChange = {},
                label = { Text("Fecha", color = Color.LightGray) },
                readOnly = true,
                enabled = false,
                colors = textFieldColors(
                    disabledTextColor = Color.White,
                    disabledLabelColor = Color.LightGray,
                    disabledContainerColor = Color(0xFF1A1B3A)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expandedState.value,
                onExpandedChange = { expandedState.value = !expandedState.value }
            ) {
                TextField(
                    value = tipoState.value,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo", color = Color.LightGray) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
                    colors = textFieldColors(
                        focusedContainerColor = Color(0xFF1A1B3A),
                        unfocusedContainerColor = Color(0xFF1A1B3A),
                        focusedLabelColor = Color(0xFF00BFFF),
                        unfocusedLabelColor = Color.LightGray
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedState.value,
                    onDismissRequest = { expandedState.value = false }
                ) {
                    opciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                tipoState.value = opcion
                                expandedState.value = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = montoState.value,
                onValueChange = { montoState.value = it },
                label = { Text("Monto", color = Color.LightGray) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = textFieldColors(
                    focusedContainerColor = Color(0xFF1A1B3A),
                    unfocusedContainerColor = Color(0xFF1A1B3A),
                    focusedLabelColor = Color(0xFF00BFFF),
                    unfocusedLabelColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = descripcionState.value,
                onValueChange = { descripcionState.value = it },
                label = { Text("Descripción", color = Color.LightGray) },
                colors = textFieldColors(
                    focusedContainerColor = Color(0xFF1A1B3A),
                    unfocusedContainerColor = Color(0xFF1A1B3A),
                    focusedLabelColor = Color(0xFF00BFFF),
                    unfocusedLabelColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            if (tipoState.value == "Ahorro") {
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = metaState.value,
                    onValueChange = { metaState.value = it },
                    label = { Text("Meta asociada (ID)", color = Color.LightGray) },
                    colors = textFieldColors(
                        focusedContainerColor = Color(0xFF1A1B3A),
                        unfocusedContainerColor = Color(0xFF1A1B3A),
                        focusedLabelColor = Color(0xFF00BFFF),
                        unfocusedLabelColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val movimiento = Movimiento(
                                tipo = tipoState.value,
                                monto = montoState.value.toDoubleOrNull() ?: 0.0,
                                fecha = fechaActual,
                                descripcion = descripcionState.value.ifBlank { null },
                                usuarioId = 1L,
                                metaId = if (tipoState.value == "Ahorro" && metaState.value.isNotBlank()) metaState.value.toLongOrNull() else null
                            )
                            // RetrofitClient.apiService.guardarMovimiento(movimiento)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Guardar Movimiento", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}
