package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Usuario

class UsuarioRepository {

    suspend fun obtenerUsuarios(): List<Usuario> {
        return RetrofitClient.apiService.obtenerUsuarios()
    }

    suspend fun obtenerUsuario(id: Long): Usuario {
        return RetrofitClient.apiService.obtenerUsuario(id)
    }

    suspend fun guardarUsuario(usuario: Usuario): Usuario {
        return RetrofitClient.apiService.guardarUsuario(usuario)
    }

    suspend fun eliminarUsuario(id: Long) {
        RetrofitClient.apiService.eliminarUsuario(id)
    }
}
