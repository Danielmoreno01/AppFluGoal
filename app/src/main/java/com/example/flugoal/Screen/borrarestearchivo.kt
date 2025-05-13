package com.example.flugoal.Screen

/*import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flugoal.Model.Usuario
import com.example.flugoal.ViewModel.UsuarioViewModel
import androidx.compose.material3.*
import androidx.compose.runtime.livedata.observeAsState


@Composable
fun UsuarioScreen(viewModel: UsuarioViewModel = viewModel()) {
    val usuarios by viewModel.usuarios.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.obtenerUsuarios()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Lista de Usuarios", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(usuarios ?: emptyList()) { usuario ->
                UsuarioCard(usuario)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UsuarioCard(usuario: Usuario) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${usuario.nombre}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Email: ${usuario.email}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}*/
