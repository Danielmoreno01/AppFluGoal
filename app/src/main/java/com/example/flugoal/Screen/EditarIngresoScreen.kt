package com.example.flugoal.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MovimientoViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
            tipoState.value = it.descripcion ?: "Ingreso"
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
