package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Interface.RetrofitClient.apiService
import com.example.flugoal.Model.Usuario

class UsuarioRepository {

    suspend fun correoYaExiste(correo: String): Boolean {
        return try {
            val response = RetrofitClient.apiService.verificarCorreo(correo)
            response.body() ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun guardarUsuario(usuario: Usuario) {
        apiService.guardarUsuario(usuario)
    }

    suspend fun obtenerUsuarioPorEmail(email: String): Usuario? {
        return apiService.obtenerUsuarioPorEmail(email)
    }

    suspend fun obtenerNombreUsuario(id: Long): String {
        return apiService.obtenerNombreUsuario(id).nombre
    }

}
