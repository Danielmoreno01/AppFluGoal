package com.example.flugoal

import androidx.compose.foundation.background
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
fun AvatarScreen(navController: NavController) {
    var avatarImage by remember { mutableStateOf("default_avatar") }
    var backgroundImage by remember { mutableStateOf("default_background") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit your Avatar", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Avatar Preview
        Box(
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
        ) {
            Text(avatarImage, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Background Preview
        Box(
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)

        ) {
            Text(backgroundImage, style = MaterialTheme.typography.titleSmall)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Save changes logic here
        }) {
            Text("Save Avatar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAvatarScreen() {
    AvatarScreen(navController = rememberNavController())
}
