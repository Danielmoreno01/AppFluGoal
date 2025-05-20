package com.example.flugoal.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flugoal.LoginScreen
import com.example.flugoal.ViewModel.MetaViewModel
import com.example.flugoal.ViewModel.MovimientoViewModel
import com.example.flugoal.ViewModel.UsuarioViewModel

import com.example.flugoal.ui.screens.RegisterScreen
import com.example.flugoal.ui.screens.WelcomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf("home")

    val usuarioViewModel: UsuarioViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "welcome",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("welcome") {
                WelcomeScreen(navController)
            }
            composable("login") {
                LoginScreen(navController, usuarioViewModel)
            }
            composable("register") {
                RegisterScreen(navController, usuarioViewModel)
            }
            composable("home") {
                HomeScreen(navController, usuarioViewModel)
            }
            composable("nuevo_movimiento") {
                NuevoMovimientoScreen(navController, usuarioViewModel)
            }
            composable("nueva_meta") {
                MetasScreen(navController, usuarioViewModel)
            }
            composable("lista_metas") {
                ListaMetasScreen(navController, usuarioViewModel)
            }
            composable(
                route = "editar_meta/{metaId}",
                arguments = listOf(navArgument("metaId") { type = NavType.LongType })
            ) { backStackEntry ->
                val metaId = backStackEntry.arguments?.getLong("metaId") ?: 0L
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val metaViewModel: MetaViewModel = viewModel()

                EditarMetaScreen(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    metaId = metaId,
                    metaViewModel = metaViewModel
                )
            }
            composable("lista_movimientos") {
                ListaMovimientosScreen(navController, usuarioViewModel)
            }

            composable("lista_egresos") {
                ListaEgresosScreen(navController, usuarioViewModel)
            }
            composable(
                route = "editar_egreso/{movimientoId}",
                arguments = listOf(navArgument("movimientoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movimientoId = backStackEntry.arguments?.getInt("movimientoId") ?: 0
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val movimientoViewModel: MovimientoViewModel = viewModel()

                EditarEgresoScreen(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    movimientoId = movimientoId.toLong(),
                    movimientoViewModel = movimientoViewModel
                )
            }

            composable("lista_ingresos") {
                ListaIngresosScreen(navController, usuarioViewModel)
            }

            composable(
                route = "editar_ingreso/{movimientoId}",
                arguments = listOf(navArgument("movimientoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movimientoId = backStackEntry.arguments?.getInt("movimientoId") ?: 0
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val movimientoViewModel: MovimientoViewModel = viewModel()

                EditarIngresoScreen(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    movimientoId = movimientoId,
                    movimientoViewModel = movimientoViewModel
                )
            }


        }
    }
}

