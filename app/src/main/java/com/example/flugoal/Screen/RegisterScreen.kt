package com.example.flugoal.ui.screens

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.flugoal.Model.Usuario
import com.example.flugoal.R
import com.example.flugoal.ViewModel.UsuarioViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterScreen(navController: NavController, usuarioViewModel: UsuarioViewModel = viewModel()) {
    val robotoFont = FontFamily(Font(R.font.concertone))

    var nombreCompleto by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repetirContrasena by remember { mutableStateOf("") }

    val correoExisteState by usuarioViewModel.correoExiste.collectAsState()
    var isCheckingCorreo by remember { mutableStateOf(false) }

    var showCorreoError by remember { mutableStateOf(false) }
    var showNombreError by remember { mutableStateOf(false) }
    var showContrasenaError by remember { mutableStateOf(false) }
    var showRepetirContrasenaError by remember { mutableStateOf(false) }

    var intentoRegistro by remember { mutableStateOf(false) }

    // Validaciones simples sin incluir si el correo existe
    fun validarCamposLocales(): Boolean {
        var valido = true

        showNombreError = nombreCompleto.isBlank()
        if (showNombreError) valido = false

        showCorreoError = correo.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        if (showCorreoError) valido = false

        showContrasenaError = contrasena.length < 6
        if (showContrasenaError) valido = false

        showRepetirContrasenaError = repetirContrasena != contrasena || repetirContrasena.isBlank()
        if (showRepetirContrasenaError) valido = false

        return valido
    }

    // Si ya se intentó registrar y la verificación terminó
    LaunchedEffect(correoExisteState, intentoRegistro, isCheckingCorreo) {
        if (intentoRegistro && !isCheckingCorreo) {
            if (correoExisteState == true) {
                showCorreoError = true
            } else {
                navController.navigate("home") // Registro exitoso
            }
            intentoRegistro = false // reset
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondoapp),
            contentDescription = "Fondo desenfocado",
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        Modifier.graphicsLayer {
                            renderEffect = RenderEffect
                                .createBlurEffect(20f, 20f, Shader.TileMode.CLAMP)
                                .asComposeRenderEffect()
                        }
                    } else {
                        Modifier
                    }
                ),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crear cuenta",
                fontSize = 40.sp,
                fontWeight = FontWeight.Light,
                color = Color(0xFF0A0101),
                textAlign = TextAlign.Center,
                fontFamily = robotoFont
            )

            Spacer(modifier = Modifier.height(100.dp))

            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = {
                    nombreCompleto = it
                    if (showNombreError && it.isNotBlank()) showNombreError = false
                },
                placeholder = { Text("Nombre Completo", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                isError = showNombreError,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    errorBorderColor = Color.Red
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            if (showNombreError) {
                Text(
                    text = "El nombre es obligatorio",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = {
                    correo = it
                    showCorreoError = false
                },
                placeholder = { Text("Correo Electrónico", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                isError = showCorreoError,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    errorBorderColor = Color.Red
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )
            if (showCorreoError) {
                Text(
                    text = when {
                        correo.isBlank() -> "El correo es obligatorio"
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> "Correo inválido"
                        correoExisteState == true -> "El correo ya está registrado"
                        else -> ""
                    },
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = contrasena,
                onValueChange = {
                    contrasena = it
                    if (showContrasenaError && it.length >= 6) showContrasenaError = false
                },
                placeholder = { Text("Contraseña", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                isError = showContrasenaError,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    errorBorderColor = Color.Red
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                visualTransformation = VisualTransformation.None
            )
            if (showContrasenaError) {
                Text(
                    text = "La contraseña debe tener al menos 6 caracteres",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = repetirContrasena,
                onValueChange = {
                    repetirContrasena = it
                    if (showRepetirContrasenaError && it == contrasena && it.isNotBlank()) showRepetirContrasenaError = false
                },
                placeholder = { Text("Repetir Contraseña", color = Color.White.copy(alpha = 0.7f)) },
                modifier = Modifier.fillMaxWidth(),
                isError = showRepetirContrasenaError,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                    focusedBorderColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    errorBorderColor = Color.Red
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                visualTransformation = VisualTransformation.None
            )
            if (showRepetirContrasenaError) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val camposValidos = validarCamposLocales()
                    if (camposValidos) {
                        intentoRegistro = true
                        isCheckingCorreo = true

                        usuarioViewModel.verificarCorreo(correo) { correoExiste ->
                            isCheckingCorreo = false

                            if (!correoExiste) {
                                // Generar fecha de registro
                                val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                                    Date()
                                )

                                // Crear objeto Usuario
                                val nuevoUsuario = Usuario(
                                    nombre = nombreCompleto,
                                    email = correo,
                                    contrasena = contrasena,
                                    fechaRegistro = fechaActual
                                )

                                // Guardar usuario
                                usuarioViewModel.guardarUsuario(nuevoUsuario)

                                // Navegar
                                navController.navigate("home")
                            } else {
                                showCorreoError = true
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = if (isCheckingCorreo) "Verificando..." else "Registrar", color = Color.White)
            }


            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("¿Ya tienes cuenta?", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Inicia sesión", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
