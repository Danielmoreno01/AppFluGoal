package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Usuario
import com.example.flugoal.Repository.UsuarioRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UsuarioViewModel : ViewModel() {

    private val repository = UsuarioRepository()

    val usuarios = MutableLiveData<List<Usuario>?>(emptyList())

    fun obtenerUsuarios() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerUsuarios()
            }
            usuarios.postValue(lista)
        }
    }

    fun guardarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.guardarUsuario(usuario)
            obtenerUsuarios()
        }
    }

    fun eliminarUsuario(id: Long) {
        viewModelScope.launch {
            repository.eliminarUsuario(id)
            obtenerUsuarios()
        }
    }
}
