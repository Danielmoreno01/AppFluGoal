import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoMovimientoScreen(
    onGuardarMovimiento: (fecha: String, tipo: String, monto: Double, descripcion: String, meta: String?) -> Unit
) {
    val context = LocalContext.current
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaActual = dateFormat.format(Date())

    var fecha by remember { mutableStateOf(fechaActual) }
    var tipo by remember { mutableStateOf("Ingreso") }
    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var meta by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    val tiposMovimiento = listOf("Ingreso", "Egreso", "Ahorro")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFF121212))
    ) {
        Text(
            text = "Nuevo Movimiento",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Red,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text("Tipo de movimiento", color = Color.White)
        tiposMovimiento.forEach { opcion ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { tipo = opcion }
                    .padding(vertical = 4.dp)
            ) {
                RadioButton(
                    selected = tipo == opcion,
                    onClick = { tipo = opcion },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.Red,
                        unselectedColor = Color.Gray
                    )
                )
                Text(opcion, color = Color.White)
            }
        }

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Red,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Red,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (tipo == "Ahorro") {
            OutlinedTextField(
                value = meta,
                onValueChange = { meta = it },
                label = { Text("Meta") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.Red,
                    unfocusedLabelColor = Color.Gray
                )
            )
        }

        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensajeError,
                color = Color.Red,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val montoDouble = monto.toDoubleOrNull()
                if (montoDouble == null || montoDouble <= 0.0) {
                    mensajeError = "El monto debe ser un número positivo"
                    return@Button
                }
                if (descripcion.isBlank()) {
                    mensajeError = "La descripción no puede estar vacía"
                    return@Button
                }
                if (tipo == "Ahorro" && meta.isBlank()) {
                    mensajeError = "Debes ingresar una meta para el ahorro"
                    return@Button
                }

                mensajeError = ""
                onGuardarMovimiento(fecha, tipo, montoDouble, descripcion, if (tipo == "Ahorro") meta else null)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Guardar", color = Color.White)
        }
    }
}
