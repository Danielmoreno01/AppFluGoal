package com.example.flugoal.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.example.flugoal.Model.Meta
import com.example.flugoal.R
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetasScreen(navController: NavHostController, usuarioViewModel: UsuarioViewModel) {
    val usuarioId = usuarioViewModel.usuarioId.collectAsState().value
    val metaViewModel: MetaViewModel = viewModel()
    val robotoFont = FontFamily(Font(R.font.concertone))
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val interactionSource = remember { MutableInteractionSource() }
    val showSuccessMessage = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
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
                    text = "¡Meta creada correctamente!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        TextField(
            value = fechaInicio,
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

        LaunchedEffect(interactionSource) {
            interactionSource.interactions.collect { interaction ->
                if (interaction is PressInteraction.Release) {
                    android.app.DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val selectedDate =
                                String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                            fechaFinState.value = selectedDate
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                }
            }
        }

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
                val nuevaMeta = Meta(
                    nombre = nombreState.value,
                    montoTotal = montoState.value.toDoubleOrNull() ?: 0.0,
                    fechaInicio = fechaInicio,
                    fechaFin = fechaFinState.value,
                    usuario = null,
                    movimientos = null
                )

                if (usuarioId != null) {
                    metaViewModel.guardarMeta(
                        meta = nuevaMeta,
                        usuarioId = usuarioId.toLong(),
                        onSuccess = {
                            showSuccessMessage.value = true
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                navController.popBackStack()
                            }
                        },
                        onError = { errorMsg ->
                            Log.e("MetasScreen", errorMsg)
                        }
                    )
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF080C23)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(
                "Guardar Meta",
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(500.dp))

        Button(
            onClick = { navController.navigate("home") {
                popUpTo("nuevaMeta") { inclusive = true }
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
