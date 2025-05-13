package com.example.flugoal

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoginScreen(navController: NavController) {
    val imageUrl = "https://uniminuto0-my.sharepoint.com/:i:/g/personal/cristian_cano-v_uniminuto_edu_co/EQ4tYamwUFJJnJHNJ4mKbSoBDADoq49cXngD7LO5FlYNmw?e=ZobgCh" // Reemplaza con tu URL directa desde OneDrive

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RemoteImage(imageUrl)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Bienvenido a FluGoal",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "Inicia sesión o regístrate para continuar",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate("login_with_email") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}

@Composable
fun RemoteImage(url: String) {
    val painter = rememberAsyncImagePainter(model = url)

    Image(
        painter = painter,
        contentDescription = "Imagen de bienvenida",
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(navController = rememberNavController())
}