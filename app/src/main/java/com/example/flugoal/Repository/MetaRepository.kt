package com.example.flugoal.Repository

import android.util.Log
import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Meta

class MetaRepository {
    suspend fun obtenerMetasPorUsuario(usuarioId: Long): List<Meta>? {
        return try {
            val response = RetrofitClient.apiService.obtenerMetasPorUsuario(usuarioId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MetaRepository", "Error: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MetaRepository", "Error al obtener metas: ${e.message}")
            null
        }
    }

    suspend fun guardarMeta(meta: Meta, usuarioId: Long): Boolean {
        return try {
            val response = RetrofitClient.apiService.guardarMeta(usuarioId, meta)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("MetaRepository", "Error al guardar meta: ${e.message}")
            false
        }
    }

    suspend fun obtenerMetaPorId(id: Long): Meta? {
        return try {
            val response = RetrofitClient.apiService.obtenerMetaPorId(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MetaRepository", "Error al obtener meta por ID: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MetaRepository", "Error al obtener meta por ID: ${e.message}")
            null
        }
    }

    suspend fun actualizarMeta(id: Long, meta: Meta): Boolean {
        return try {
            val response = RetrofitClient.apiService.actualizarMeta(id, meta)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("MetaRepository", "Error al actualizar meta: ${e.message}")
            false
        }
    }

    suspend fun eliminarMeta(id: Long): Boolean {
        return try {
            val response = RetrofitClient.apiService.eliminarMeta(id)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("MetaRepository", "Error al eliminar meta: ${e.message}")
            false
        }
    }

}
