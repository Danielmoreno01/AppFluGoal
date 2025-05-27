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

    suspend fun obtenerHistorialMovimientos(usuarioId: Long): List<Movimiento> {
        return try {
            val response = RetrofitClient.apiService.obtenerHistorialMovimientos(usuarioId)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("MovimientoRepository", "Error al obtener historial: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al obtener historial: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerEgresosPorUsuario(usuarioId: Long): List<Movimiento> {
        return try {
            val response = RetrofitClient.apiService.obtenerEgresosPorUsuario(usuarioId)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("MovimientoRepository", "Error al obtener egresos: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al obtener egresos: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerIngresosPorUsuario(usuarioId: Long): List<Movimiento> {
        return try {
            val response = RetrofitClient.apiService.obtenerIngresosPorUsuario(usuarioId)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("MovimientoRepository", "Error al obtener ingresos: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al obtener ingresos: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerIngresosMetasPorUsuario(usuarioId: Long): List<Movimiento> {
        return try {
            val response = RetrofitClient.apiService.obtenerIngresosMetasPorUsuario(usuarioId)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("MovimientoRepository", "Error al obtener ingresos para metas: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al obtener ingresos para metas: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerAhorrosPorUsuario(usuarioId: Long): List<Movimiento> {
        return try {
            val response = RetrofitClient.apiService.obtenerAhorrosPorUsuario(usuarioId)
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                Log.e("MovimientoRepository", "Error al obtener ahorros: ${response.code()} - ${response.message()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al obtener ahorros: ${e.message}")
            emptyList()
        }
    }

    suspend fun actualizarMovimiento(movimientoId: Int, movimiento: Movimiento): Movimiento? {
        return try {
            val response = RetrofitClient.apiService.actualizarMovimiento(movimientoId, movimiento)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MovimientoRepository", "Error al actualizar movimiento: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al actualizar movimiento: ${e.message}")
            null
        }
    }

    suspend fun eliminarMovimiento(movimientoId: Int): Boolean {
        return try {
            val response = RetrofitClient.apiService.eliminarMovimiento(movimientoId)
            response.isSuccessful
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al eliminar movimiento: ${e.message}")
            false
        }
    }

    suspend fun obtenerMovimientoPorId(movimientoId: Int): Movimiento? {
        return try {
            val response = RetrofitClient.apiService.obtenerMovimientoPorId(movimientoId)
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("MovimientoRepository", "Error al obtener movimiento por ID: ${response.code()} - ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("MovimientoRepository", "Excepción al obtener movimiento por ID: ${e.message}")
            null
        }
    }

}
