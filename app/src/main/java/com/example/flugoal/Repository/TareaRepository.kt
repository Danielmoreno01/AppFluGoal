package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Tarea

class TareaRepository {

    suspend fun obtenerTareas(): List<Tarea> {
        return RetrofitClient.apiService.obtenerTareas()
    }

    suspend fun obtenerTarea(id: Long): Tarea {
        return RetrofitClient.apiService.obtenerTarea(id)
    }

    suspend fun guardarTarea(tarea: Tarea): Tarea {
        return RetrofitClient.apiService.guardarTarea(tarea)
    }

    suspend fun eliminarTarea(id: Long) {
        RetrofitClient.apiService.eliminarTarea(id)
    }
}
