package com.example.flugoal.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarMetaScreen(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    metaId: Long,
    metaViewModel: MetaViewModel = viewModel()
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val robotoFont = FontFamily(Font(R.font.concertone))
    val interactionSource = remember { MutableInteractionSource() }
    val showSuccessMessage = remember { mutableStateOf(false) }

    val metaSeleccionada by metaViewModel.metaSeleccionada.collectAsState()

    val nombreState = remember(metaSeleccionada) { mutableStateOf(metaSeleccionada?.nombre ?: "") }
    val montoState = remember(metaSeleccionada) { mutableStateOf(metaSeleccionada?.montoTotal?.toString() ?: "") }
    val fechaInicioState = remember(metaSeleccionada) { mutableStateOf(metaSeleccionada?.fechaInicio ?: "") }
    val fechaFinState = remember(metaSeleccionada) { mutableStateOf(metaSeleccionada?.fechaFin ?: "") }

    LaunchedEffect(metaId) {
        metaViewModel.cargarMetaPorId(metaId) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is PressInteraction.Release) {
                android.app.DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        val selectedDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                        fechaFinState.value = selectedDate
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
            text = "Editar Meta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF080C23),
            fontFamily = robotoFont,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        if (metaSeleccionada != null) {
            Spacer(modifier = Modifier.height(12.dp))

            if (showSuccessMessage.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF4CAF50), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "¡Meta actualizada correctamente!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            TextField(
                value = fechaInicioState.value,
                onValueChange = {},
                readOnly = true,
                enabled = false,
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.DarkGray,
                    disabledLabelColor = Color.LightGray,
                ),
                label = { Text("Fecha de Inicio") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = nombreState.value,
                onValueChange = { nombreState.value = it },
                label = { Text("Nombre de la Meta") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFF080C23),
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = montoState.value,
                onValueChange = { montoState.value = it },
                label = { Text("Monto Total") },
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
                value = fechaFinState.value,
                onValueChange = {},
                label = { Text("Fecha de Fin (yyyy-MM-dd)") },
                readOnly = true,
                interactionSource = interactionSource,
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
                    val montoDouble = montoState.value.toDoubleOrNull() ?: 0.0
                    val metaActualizada = metaSeleccionada!!.copy(
                        nombre = nombreState.value,
                        montoTotal = montoDouble,
                        fechaFin = fechaFinState.value
                    )

                    metaViewModel.actualizarMeta(
                        metaId = metaId,
                        meta = metaActualizada,
                        onSuccess = {
                            showSuccessMessage.value = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                navController.popBackStack()
                            }
                        },
                        onError = {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF080C23)),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Guardar Cambios", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        } else {
            CircularProgressIndicator(color = Color(0xFF080C23))
        }
    }
}
