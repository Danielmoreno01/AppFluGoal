package com.example.flugoal.ui.screens

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import com.example.flugoal.Model.Usuario
import com.example.flugoal.R
import com.example.flugoal.ViewModel.UsuarioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun RegisterScreen(navController: NavController, usuarioViewModel: UsuarioViewModel) {
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

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun validarCamposLocales(): Boolean {
        var valido = true

        showNombreError = nombreCompleto.isBlank()
        if (showNombreError) valido = false

        showCorreoError =
            correo.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
        if (showCorreoError) valido = false

        val tieneCaracterEspecial = contrasena.any { it.isLetterOrDigit().not() }
        showContrasenaError = contrasena.length < 8 || !tieneCaracterEspecial
        if (showContrasenaError) valido = false

        showRepetirContrasenaError = repetirContrasena != contrasena || repetirContrasena.isBlank()
        if (showRepetirContrasenaError) valido = false

        return valido
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
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
                placeholder = {
                    Text(
                        "Correo Electrónico",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
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
                        !android.util.Patterns.EMAIL_ADDRESS.matcher(correo)
                            .matches() -> "Correo inválido"

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
                visualTransformation = PasswordVisualTransformation()
            )
            if (showContrasenaError) {
                Text(
                    text = "Minimo 8 caracteres y simbolos",
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
                    if (showRepetirContrasenaError && it == contrasena && it.isNotBlank()) showRepetirContrasenaError =
                        false
                },
                placeholder = {
                    Text(
                        "Repetir Contraseña",
                        color = Color.White.copy(alpha = 0.7f)
                    )
                },
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
                visualTransformation = PasswordVisualTransformation()
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
                        isCheckingCorreo = true

                        usuarioViewModel.verificarCorreo(correo) { correoExiste ->
                            isCheckingCorreo = false

                            if (correoExiste) {
                                showCorreoError = true
                            } else {
                                val fechaActual = SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss",
                                    Locale.getDefault()
                                ).format(Date())
                                val nuevoUsuario = Usuario(
                                    nombre = nombreCompleto,
                                    email = correo,
                                    contrasena = contrasena,
                                    fechaRegistro = fechaActual
                                )

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "¡Cuenta creada con éxito!, Bienvenido",
                                        duration = SnackbarDuration.Short
                                    )
                                }

                                usuarioViewModel.guardarUsuarioYObtenerId(nuevoUsuario) { id ->
                                    scope.launch {
                                        delay(500)
                                        navController.navigate("home")
                                    }
                                }
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = if (isCheckingCorreo) "Verificando..." else "Registrar",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "¿Ya tienes cuenta?",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Inicia Sesion",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                )
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            snackbar = { data ->
                Snackbar(
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .defaultMinSize(minWidth = 180.dp),
                    containerColor = Color(0xFF424242),
                    contentColor = Color.White,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}