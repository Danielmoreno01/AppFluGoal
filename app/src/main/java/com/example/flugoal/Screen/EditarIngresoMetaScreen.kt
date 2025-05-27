package com.example.flugoal.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.flugoal.Model.Meta
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.MovimientoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarIngresoMetaScreen(
    movimientoId: Int,
    navController: NavController,
    movimientoViewModel: MovimientoViewModel,
    usuarioId: String,
    metaViewModel: MetaViewModel
) {
    val movimientoSeleccionado by movimientoViewModel.movimientoSeleccionado.collectAsState()
    val metas by metaViewModel.metas.collectAsState()
    val context = LocalContext.current

    val robotoFont = FontFamily(Font(R.font.concertone))

    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val metaExpanded = remember { mutableStateOf(false) }
    val selectedMeta = remember { mutableStateOf<Meta?>(null) }

    LaunchedEffect(movimientoId) {
        movimientoViewModel.cargarMovimientoPorId(movimientoId)
        metaViewModel.cargarMetasPorUsuario(usuarioId)
    }

    LaunchedEffect(movimientoSeleccionado) {
        movimientoSeleccionado?.let {
            monto = it.monto.toString()
            descripcion = it.descripcion
            selectedMeta.value = it.presupuesto
            it.presupuesto?.let { meta -> movimientoViewModel.actualizarMetaActual(meta) }
        }
    }

    if (movimientoSeleccionado == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color(0xFF080C23))
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
            text = "Editar Ingreso a Meta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF080C23),
            fontFamily = robotoFont,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFF81D4FA),
                unfocusedIndicatorColor = Color.Gray,
                containerColor = Color.White,
                focusedLabelColor = Color(0xFF81D4FA)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color(0xFF81D4FA),
                unfocusedIndicatorColor = Color.Gray,
                containerColor = Color.White,
                focusedLabelColor = Color(0xFF81D4FA)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        ExposedDropdownMenuBox(
            expanded = metaExpanded.value,
            onExpandedChange = { metaExpanded.value = !metaExpanded.value }
        ) {
            TextField(
                value = selectedMeta.value?.nombre ?: "Seleccionar Meta",
                onValueChange = {},
                readOnly = true,
                label = { Text("Meta asociada") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = metaExpanded.value) },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color(0xFF81D4FA),
                    unfocusedIndicatorColor = Color.Gray,
                    containerColor = Color.White,
                    focusedLabelColor = Color(0xFF81D4FA)
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
                            selectedMeta.value = meta
                            movimientoViewModel.actualizarMetaActual(meta)
                            metaExpanded.value = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        selectedMeta.value?.let { meta ->
            val totalIngresoMeta by movimientoViewModel.totalIngresadoMeta.collectAsState()
            val montoRestante = meta.montoTotal - (totalIngresoMeta ?: 0.0)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Meta total: $${"%.2f".format(meta.montoTotal)}",
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
                    text = "Restante por ingresar: $${"%.2f".format(montoRestante)}",
                    color = Color(0xFF9C27B0),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val montoDouble = monto.toDoubleOrNull()
                when {
                    montoDouble == null || montoDouble <= 0 -> {
                        Toast.makeText(context, "Monto inválido", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    descripcion.isBlank() -> {
                        Toast.makeText(context, "Ingrese una descripción", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    selectedMeta.value == null -> {
                        Toast.makeText(context, "Seleccione una meta", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    !movimientoViewModel.validarMontoMeta(montoDouble) -> {
                        Toast.makeText(context, "El monto excede el límite disponible", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                }

                val movimientoActualizado = montoDouble?.let {
                    movimientoSeleccionado?.copy(
                        monto = it,
                        descripcion = descripcion,
                        presupuesto = selectedMeta.value
                    )
                }

                movimientoActualizado?.let {
                    movimientoViewModel.actualizarMovimiento(
                        movimientoId = movimientoId,
                        movimiento = it,
                        onSuccess = {
                            Toast.makeText(context, "Movimiento actualizado", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF81D4FA)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Cambios", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
