package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Meta

class MetaRepository {

    suspend fun obtenerMetas(): List<Meta> {
        return RetrofitClient.apiService.obtenerMetas()
    }

    suspend fun obtenerMeta(id: Long): Meta {
        return RetrofitClient.apiService.obtenerMeta(id)
    }

    suspend fun guardarMeta(meta: Meta): Meta {
        return RetrofitClient.apiService.guardarMeta(meta)
    }

    suspend fun eliminarMeta(id: Long) {
        RetrofitClient.apiService.eliminarMeta(id)
    }
}
