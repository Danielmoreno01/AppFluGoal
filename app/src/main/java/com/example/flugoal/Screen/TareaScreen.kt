package com.example.flugoal.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Text


@Composable
fun TareasScreen(navController: NavController) {
    val tareasList = remember { mutableStateOf(listOf("Task 1", "Task 2", "Task 3")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("My Tasks", style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(16.dp))

        tareasList.value.forEach { task ->
            Text(task, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            // Add new task logic here
        }) {
            Text("Add Task")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTareasScreen() {
    TareasScreen(navController = rememberNavController())
}
