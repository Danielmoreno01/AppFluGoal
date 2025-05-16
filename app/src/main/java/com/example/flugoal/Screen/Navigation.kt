package com.example.flugoal.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flugoal.LoginScreen
import com.example.flugoal.ui.screens.RegisterScreen
import com.example.flugoal.ui.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in bottomNavItems.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController, currentRoute = currentRoute) {}
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("welcome") { WelcomeScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("register") { RegisterScreen(navController) }
            composable("movimientos") { MovimientosScreen(navController) }
            composable("home") { HomeScreen(navController) }
            composable("nuevo_movimiento") { NuevoMovimientoScreen(navController) }
            composable("avatares") { AvatarScreen(navController) }
            composable("metas") { MetasScreen(navController) }
            composable("perfil") { PerfilScreen(navController) } // ← Aquí solo contenido, sin Scaffold
            composable("tareas") { TareasScreen(navController) }
        }
    }
}
