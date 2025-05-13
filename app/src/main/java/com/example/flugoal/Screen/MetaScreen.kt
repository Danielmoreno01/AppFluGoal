package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.*
import androidx.compose.material3.Text

@Composable
fun MetasScreen(navController: NavController) {
    val metasList = remember { mutableStateOf(listOf("Goal 1", "Goal 2", "Goal 3")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("My Goals", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        metasList.value.forEach { goal ->
            Text(goal, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Add new goal logic here
        }) {
            Text("Add Goal")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMetasScreen() {
    MetasScreen(navController = rememberNavController())
}
