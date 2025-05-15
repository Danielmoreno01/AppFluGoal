package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Usuario
import com.example.flugoal.Repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()
    private val _correoExiste = MutableStateFlow<Boolean?>(null)
    val correoExiste: StateFlow<Boolean?> get() = _correoExiste

    val usuarios = MutableLiveData<List<Usuario>?>(emptyList())

    fun guardarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.guardarUsuario(usuario)
        }
    }

    fun verificarCorreo(correo: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val existe = repository.correoYaExiste(correo)
            _correoExiste.value = existe
            onResult(existe)
        }
    }

}
