package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Movimiento

class MovimientoRepository {

    suspend fun obtenerMovimientos(): List<Movimiento> {
        return RetrofitClient.apiService.obtenerMovimientos()
    }

    suspend fun obtenerMovimiento(id: Long): Movimiento {
        return RetrofitClient.apiService.obtenerMovimiento(id)
    }

    suspend fun guardarMovimiento(movimiento: Movimiento): Movimiento {
        return RetrofitClient.apiService.guardarMovimiento(movimiento)
    }

    suspend fun eliminarMovimiento(id: Long) {
        RetrofitClient.apiService.eliminarMovimiento(id)
    }
}
