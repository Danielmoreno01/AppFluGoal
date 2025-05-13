package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RecompensasScreen(navController: NavController) {
    val recompensasList = remember { mutableStateOf(listOf("Reward 1", "Reward 2", "Reward 3")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("My Rewards", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        recompensasList.value.forEach { reward ->
            Text(reward, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Redeem reward logic here
        }) {
            Text("Redeem Reward")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecompensasScreen() {
    RecompensasScreen(navController = rememberNavController())
}
