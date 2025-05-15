package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Interface.RetrofitClient.apiService
import com.example.flugoal.Model.Usuario

class UsuarioRepository {

    suspend fun guardarUsuario(usuario: Usuario): Usuario {
        return RetrofitClient.apiService.guardarUsuario(usuario)
    }

    suspend fun correoYaExiste(correo: String): Boolean {
        return try {
            val response = apiService.verificarCorreoExistente(correo)
            response.body() == true
        } catch (e: Exception) {
            false
        }
    }
}
