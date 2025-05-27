package com.example.flugoal.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Usuario
import com.example.flugoal.Repository.UsuarioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val _correoExiste = MutableStateFlow<Boolean?>(null)
    val correoExiste: StateFlow<Boolean?> get() = _correoExiste

    private val _usuarioId = MutableStateFlow<String?>(null)
    val usuarioId: StateFlow<String?> = _usuarioId

    fun establecerUsuarioId(id: String) {
        _usuarioId.value = id
    }

    fun cerrarSesion() {
        _usuarioId.value = null
        _usuarioNombre.value = ""
    }
    fun guardarUsuarioYObtenerId(usuario: Usuario, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            try {
                repository.guardarUsuario(usuario)
                delay(500)
                System.out.println("Buscando usuario con email: " + usuario.email);
                val usuarioGuardado = repository.obtenerUsuarioPorEmail(usuario.email)
                usuarioGuardado?.id?.let { id ->
                    establecerUsuarioId(id.toString())
                    onSuccess(id)
                    Log.d("UsuarioViewModel", "Cargando usuario con ID: $id")
                } ?: Log.e("UsuarioViewModel", "Error: id del usuario guardado es null")
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error al guardar y obtener usuario: ${e.message}")
            }
        }
    }

    fun verificarCorreo(correo: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val existe = repository.correoYaExiste(correo)
            onResult(existe)
        }
    }

    private val _usuarioNombre = MutableStateFlow("")
    val usuarioNombre: StateFlow<String> = _usuarioNombre

    fun cargarNombreUsuarioPorId(id: Long) = viewModelScope.launch {
        runCatching { repository.obtenerNombreUsuario(id) }
            .onSuccess { nombre -> _usuarioNombre.value = nombre }
            .onFailure { Log.e("VM", "Error: ${it.message}") }
    }

    fun iniciarSesion(email: String, contrasena: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val usuario = repository.obtenerUsuarioPorEmail(email)
                if (usuario != null && usuario.contrasena == contrasena) {
                    establecerUsuarioId(usuario.id.toString())
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("UsuarioViewModel", "Error al iniciar sesión: ${e.message}")
                onResult(false)
            }
        }
    }

}
