package com.example.flugoal.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.flugoal.Model.Meta
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.Model.Usuario
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.MovimientoViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoMovimientoScreen(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    metaViewModel: MetaViewModel = viewModel(),
    movimientoViewModel: MovimientoViewModel = viewModel()
) {
    val opciones = listOf("Ingreso", "Egreso", "Ahorro", "IngresoMeta")
    val usuarioId = usuarioViewModel.usuarioId.collectAsState().value
    val metas by metaViewModel.metas.collectAsState()
    val totalIngresoMeta by movimientoViewModel.totalIngresadoMeta.collectAsState()
    val metaActual by movimientoViewModel.metaActual.collectAsState()

    val robotoFont = FontFamily(Font(R.font.concertone))

    val tipoState = remember { mutableStateOf("") }
    val tipoExpanded = remember { mutableStateOf(false) }

    val montoState = remember { mutableStateOf("") }
    val descripcionState = remember { mutableStateOf("") }

    val metaExpanded = remember { mutableStateOf(false) }
    val metaSeleccionada = remember { mutableStateOf<Meta?>(null) }
    val montoRestante = remember { mutableStateOf(0.0) }
    val hayError = remember { mutableStateOf(false) }
    val mensajeError = remember { mutableStateOf("") }
    val showSuccessMessage = remember { mutableStateOf(false) }

    val fechaActual = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    LaunchedEffect(usuarioId) {
        if (!usuarioId.isNullOrBlank()) {
            metaViewModel.cargarMetasPorUsuario(usuarioId!!)
        }
    }

    LaunchedEffect(metaSeleccionada.value) {
        metaSeleccionada.value?.let {
            movimientoViewModel.actualizarMetaActual(it)
        }
    }

    LaunchedEffect(totalIngresoMeta, metaActual) {
        metaActual?.let { meta ->
            val total = totalIngresoMeta ?: 0.0
            montoRestante.value = meta.montoTotal - total
        }
    }

    LaunchedEffect(montoState.value, montoRestante.value) {
        if (tipoState.value == "IngresoMeta" && metaSeleccionada.value != null) {
            val montoIngresado = montoState.value.toDoubleOrNull() ?: 0.0
            if (montoIngresado > montoRestante.value) {
                hayError.value = true
                mensajeError.value = "El monto excede lo restante para esta meta"
            } else {
                hayError.value = false
                mensajeError.value = ""
            }
        } else {
            hayError.value = false
            mensajeError.value = ""
        }
    }

    if (usuarioId == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Black)
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nuevo Movimiento",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF080C23),
            fontFamily = robotoFont,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (showSuccessMessage.value) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "¡Movimiento creado correctamente!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        TextField(
            value = fechaActual,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            colors = textFieldColors(
                disabledTextColor = Color.DarkGray,
                disabledLabelColor = Color.LightGray,
                disabledContainerColor = Color(0xFFECECEC)
            ),
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = tipoExpanded.value,
            onExpandedChange = { tipoExpanded.value = !tipoExpanded.value }
        ) {
            TextField(
                value = tipoState.value,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = tipoExpanded.value) },
                colors = textFieldColors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = tipoExpanded.value,
                onDismissRequest = { tipoExpanded.value = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            tipoState.value = opcion
                            tipoExpanded.value = false
                            metaSeleccionada.value = null
                            montoState.value = ""
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = montoState.value,
            onValueChange = { montoState.value = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = textFieldColors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                errorContainerColor = Color(0xFFFCEAEA),
                errorCursorColor = Color.Red
            ),
            isError = hayError.value,
            supportingText = {
                if (hayError.value) {
                    Text(
                        text = mensajeError.value,
                        color = Color.Red,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = descripcionState.value,
            onValueChange = { descripcionState.value = it },
            label = { Text("Descripción") },
            colors = textFieldColors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (tipoState.value == "IngresoMeta") {
            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = metaExpanded.value,
                onExpandedChange = { metaExpanded.value = !metaExpanded.value }
            ) {
                TextField(
                    value = metaSeleccionada.value?.nombre ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Meta asociada") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = metaExpanded.value) },
                    colors = textFieldColors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = metaExpanded.value,
                    onDismissRequest = { metaExpanded.value = false }
                ) {
                    metas.forEach { meta ->
                        DropdownMenuItem(
                            text = { Text(meta.nombre) },
                            onClick = {
                                metaSeleccionada.value = meta
                                metaExpanded.value = false
                                montoState.value = ""
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (metaSeleccionada.value != null) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Meta total: $${"%.2f".format(metaSeleccionada.value?.montoTotal ?: 0.0)}",
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Ya has ingresado: $${"%.2f".format(totalIngresoMeta ?: 0.0)}",
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Restante por ingresar: $${"%.2f".format(montoRestante.value)}",
                        color = Color(0xFF9C27B0),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val monto = montoState.value.toDoubleOrNull() ?: 0.0

                val movimiento = Movimiento(
                    tipo = tipoState.value,
                    monto = monto,
                    fecha = fechaActual,
                    descripcion = descripcionState.value,
                    usuario = null,
                    presupuesto = if (tipoState.value == "IngresoMeta") metaSeleccionada.value else null
                )
                movimientoViewModel.guardarMovimiento(
                    movimiento = movimiento,
                    usuarioId = usuarioId.toLong(),
                    onSuccess = {
                        showSuccessMessage.value = true
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            navController.popBackStack()
                        }
                    },
                    onError = { errorMsg ->
                        Log.e("NuevoMovimientoScreen", "Error al guardar: $errorMsg")
                    }
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF080C23)),
            shape = RoundedCornerShape(12.dp),
            enabled = !(hayError.value ||
                    (tipoState.value.isEmpty()) ||
                    (montoState.value.toDoubleOrNull() ?: 0.0) <= 0 ||
                    (tipoState.value == "IngresoMeta" && metaSeleccionada.value == null)),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(
                "Guardar Movimiento",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(500.dp))

        Button(
            onClick = { navController.navigate("home") {
                popUpTo("nuevoMovimiento") { inclusive = true }
            } },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 12.dp)
        ) {
            Text(
                text = "Volver al Inicio",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}