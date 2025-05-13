package com.example.flugoal.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun HomeScreen(navController: NavController) {
    val userName = remember { mutableStateOf("John Doe") }
    val progress = remember { mutableStateOf(75) } // Example of progress (percent)
    val recentActivities = remember { mutableStateOf(listOf("Completed task 1", "Completed task 2")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Welcome, ${userName.value}", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Progress: ${progress.value}%", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Text("Recent Activities", style = MaterialTheme.typography.titleMedium)

        recentActivities.value.forEach { activity ->
            Text(activity, style = MaterialTheme.typography.titleSmall)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate("tasks")
        }) {
            Text("Go to Tasks")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate("rewards")
        }) {
            Text("Go to Rewards")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = rememberNavController())
}