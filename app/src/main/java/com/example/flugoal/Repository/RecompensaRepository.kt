package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Recompensa

class RecompensaRepository {

    suspend fun obtenerRecompensas(): List<Recompensa> {
        return RetrofitClient.apiService.obtenerRecompensas()
    }

    suspend fun obtenerRecompensa(id: Long): Recompensa {
        return RetrofitClient.apiService.obtenerRecompensa(id)
    }

    suspend fun guardarRecompensa(recompensa: Recompensa): Recompensa {
        return RetrofitClient.apiService.guardarRecompensa(recompensa)
    }

    suspend fun eliminarRecompensa(id: Long) {
        RetrofitClient.apiService.eliminarRecompensa(id)
    }
}
