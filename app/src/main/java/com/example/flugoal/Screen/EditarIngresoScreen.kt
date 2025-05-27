package com.example.flugoal.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MovimientoViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel

import java.util.Calendar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarIngresoScreen(
    navController: NavController,
    movimientoId: Int,
    movimientoViewModel: MovimientoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel
) {
    val movimientoSeleccionado by movimientoViewModel.movimientoSeleccionado.collectAsState()
    val usuarioId = usuarioViewModel.usuarioId.collectAsState().value
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val robotoFont = FontFamily(Font(R.font.concertone))
    val interactionSource = remember { MutableInteractionSource() }
    val showSuccessMessage = remember { mutableStateOf(false) }

    val montoState = remember { mutableStateOf("") }
    val fechaState = remember { mutableStateOf("") }
    val descripcionState = remember { mutableStateOf("") }
    val tipoState = remember { mutableStateOf("Ingreso") }

    LaunchedEffect(movimientoId) {
        movimientoViewModel.cargarMovimientoPorId(movimientoId)
    }

    LaunchedEffect(movimientoSeleccionado) {
        movimientoSeleccionado?.let {
            montoState.value = it.monto?.toString() ?: ""
            fechaState.value = it.fecha ?: ""
            descripcionState.value = it.descripcion ?: ""
            tipoState.value = it.tipo ?: "Ingreso"
        }
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                android.app.DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                        fechaState.value = selectedDate
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Editar Ingreso",
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
                    text = "¡Ingreso actualizado correctamente!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        TextField(
            value = tipoState.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF080C23),
                unfocusedIndicatorColor = Color.Gray,
                disabledTextColor = Color.DarkGray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = montoState.value,
            onValueChange = { montoState.value = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF080C23),
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = fechaState.value,
            onValueChange = {},
            label = { Text("Fecha (yyyy-MM-dd)") },
            readOnly = true,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF080C23),
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = descripcionState.value,
            onValueChange = { descripcionState.value = it },
            label = { Text("Descripción") },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF080C23),
                unfocusedIndicatorColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val monto = montoState.value.toDoubleOrNull()
                val fecha = fechaState.value
                val descripcion = descripcionState.value

                if (monto == null || fecha.isBlank()) {
                    Toast.makeText(context, "Monto y Fecha son obligatorios", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val movimientoEditado = movimientoSeleccionado?.copy(
                    monto = monto,
                    fecha = fecha,
                    descripcion = descripcion,
                    tipo = tipoState.value
                ) ?: return@Button

                movimientoViewModel.actualizarMovimiento(
                    movimientoId,
                    movimientoEditado,
                    onSuccess = {
                        showSuccessMessage.value = true
                        navController.popBackStack()
                    },
                    onError = { errorMsg ->
                        Toast.makeText(context, "Error: $errorMsg", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Guardar Cambios")
        }
    }
}
