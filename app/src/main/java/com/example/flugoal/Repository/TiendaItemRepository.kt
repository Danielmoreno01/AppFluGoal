package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.TiendaItem

class TiendaItemRepository {

    suspend fun obtenerItemsTienda(): List<TiendaItem> {
        return RetrofitClient.apiService.obtenerItemsTienda()
    }

    suspend fun obtenerItemTienda(id: Long): TiendaItem {
        return RetrofitClient.apiService.obtenerItemTienda(id)
    }

    suspend fun guardarItemTienda(item: TiendaItem): TiendaItem {
        return RetrofitClient.apiService.guardarItemTienda(item)
    }

    suspend fun eliminarItemTienda(id: Long) {
        RetrofitClient.apiService.eliminarItemTienda(id)
    }
}
