package com.example.flugoal.Repository

import android.util.Log
import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Interface.RetrofitClient.apiService
import com.example.flugoal.Model.Movimiento

class MovimientoRepository {

    suspend fun guardarMovimiento(movimiento: Movimiento, usuarioId: Long): Movimiento? {
        return try {
            val response = RetrofitClient.apiService.guardarMovimiento(usuarioId, movimiento)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MovimientoRepository", "Error al guardar movimiento: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al guardar movimiento: ${e.message}")
            null
        }
    }

    suspend fun obtenerTotalIngresadoEnMeta(metaId: Long): Double {
        return apiService.obtenerTotalIngresadoEnMeta(metaId)
    }

}
