package com.example.flugoal.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Movimiento
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoMovimientoScreen(navController: NavController) {
    val scope = rememberCoroutineScope()

    val tipos = listOf("Ingreso", "Egreso")
    var tipoSeleccionado by remember { mutableStateOf(tipos[0]) }

    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Fecha editable con valor inicial hoy
    var fecha by remember { mutableStateOf(LocalDate.now().format(DateTimeFormatter.ISO_DATE)) }

    var mensajeEstado by remember { mutableStateOf<String?>(null) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                currentRoute = navController.currentBackStackEntry?.destination?.route,
                onCreateClick = { navController.navigate("nuevo_movimiento") }
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Selector tipo: dos opciones como botones radio
            Text(
                text = "Tipo de Movimiento",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                tipos.forEach { tipo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .selectable(
                                selected = (tipo == tipoSeleccionado),
                                onClick = { tipoSeleccionado = tipo }
                            )
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = (tipo == tipoSeleccionado),
                            onClick = { tipoSeleccionado = tipo },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFFE65100),
                                unselectedColor = Color.White
                            )
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(text = tipo, color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo monto
            TextField(
                value = monto,
                onValueChange = { monto = it },
                label = { Text("Monto", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    focusedIndicatorColor = Color(0xFFE65100),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFFE65100),
                    focusedLabelColor = Color(0xFFE65100),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo descripción (opcional)
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    focusedIndicatorColor = Color(0xFFE65100),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFFE65100),
                    focusedLabelColor = Color(0xFFE65100),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo fecha (editable)
            TextField(
                value = fecha,
                onValueChange = { newFecha ->
                    // Validar formato YYYY-MM-DD básico (puedes mejorar la validación)
                    if (newFecha.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                        fecha = newFecha
                    }
                },
                label = { Text("Fecha (YYYY-MM-DD)", color = Color.White) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    focusedIndicatorColor = Color(0xFFE65100),
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color(0xFFE65100),
                    focusedLabelColor = Color(0xFFE65100),
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                ),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val montoValido = monto.toDoubleOrNull()
                    if (montoValido == null || montoValido <= 0) {
                        mensajeEstado = "Ingresa un monto válido mayor a cero"
                        return@Button
                    }

                    // Validar fecha formato básico
                    if (!fecha.matches(Regex("\\d{4}-\\d{2}-\\d{2}"))) {
                        mensajeEstado = "Ingresa una fecha válida en formato YYYY-MM-DD"
                        return@Button
                    }

                    scope.launch {
                        try {
                            val movimiento = Movimiento(
                                id = null,
                                tipo = tipoSeleccionado.lowercase(),
                                monto = montoValido,
                                fecha = fecha,
                                descripcion = if (descripcion.isBlank()) null else descripcion,
                                usuario = null,
                                presupuesto = null
                            )
                            RetrofitClient.apiService.guardarMovimiento(movimiento)
                            mensajeEstado = "Movimiento guardado correctamente"
                            monto = ""
                            descripcion = ""
                            fecha = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
                            tipoSeleccionado = tipos[0]
                            navController.popBackStack()
                        } catch (e: Exception) {
                            mensajeEstado = "Error al guardar: ${e.message}"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100))
            ) {
                Text("Guardar", color = Color.White)
            }

            mensajeEstado?.let { msg ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = msg,
                    color = if (msg.contains("Error", ignoreCase = true)) Color.Red else Color.Green,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
