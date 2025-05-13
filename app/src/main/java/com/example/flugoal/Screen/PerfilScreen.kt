// PerfilScreen.kt
package com.example.flugoal

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun PerfilScreen(navController: NavController) {
    val userName = remember { mutableStateOf("John Doe") }
    val userEmail = remember { mutableStateOf("johndoe@example.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Profile", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Name: ${userName.value}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Email: ${userEmail.value}", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Edit profile logic here
        }) {
            Text("Edit Profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPerfilScreen() {
    PerfilScreen(navController = rememberNavController())
}
