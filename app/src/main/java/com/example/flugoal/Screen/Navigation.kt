package com.example.flugoal.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flugoal.AvatarScreen
import com.example.flugoal.LoginScreen

import com.example.flugoal.ui.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        // Agrega aquí otros composables si decides usar las otras clases (como Meta.kt o Perfil.kt) directamente

    }
}
