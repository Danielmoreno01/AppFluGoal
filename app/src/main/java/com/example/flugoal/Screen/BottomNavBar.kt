package com.example.flugoal.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(navController: NavHostController, currentRoute: String?) {
    // Usando colores verdes más suaves y modernos
    val softGreen = Color(0xFF81C784)       // Verde claro/suave primario
    val lighterGreen = Color(0xFFA5D6A7)    // Verde más claro para la barra
    val accentGreen = Color(0xFF000000)     // Verde de acento para el botón flotante
    val darkGreen = Color(0xFF388E3C)       // Verde más oscuro para elementos seleccionados

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Barra de navegación principal
        NavigationBar(
            containerColor = lighterGreen,
            contentColor = Color.White,
            tonalElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Iterar sobre los elementos de navegación
                bottomNavItems.forEachIndexed { index, item ->
                    NavItem(
                        icon = item.icon,
                        label = item.title,
                        selected = currentRoute == item.route,
                        onClick = { navController.navigate(item.route) }
                    )

                    // Agregar un Spacer solo si no es el último elemento
                    if (index < bottomNavItems.size - 1) {
                        Spacer(modifier = Modifier.width(16.dp)) // Ajusta el ancho según sea necesario
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navController.navigate("create") },
            containerColor = accentGreen,
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(8.dp),
            modifier = Modifier
                .size(56.dp)
                .offset(y = (-8).dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Crear",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Componente para cada elemento de navegación
@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    // Colores de los elementos seleccionados
    val selectedColor = Color.DarkGray
    val unselectedColor = Color.Gray

    // Ajustar el color basado en si el item está seleccionado o no
    val iconTint = if (selected) selectedColor else unselectedColor
    val textColor = if (selected) selectedColor else unselectedColor

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

