package com.example.flugoal.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Meta
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.Repository.MovimientoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovimientoViewModel : ViewModel() {
    private val repository = MovimientoRepository()

    private val _totalIngresadoMeta = MutableStateFlow<Double?>(null)
    val totalIngresadoMeta: StateFlow<Double?> get() = _totalIngresadoMeta

    private val _metaActual = MutableStateFlow<Meta?>(null)
    val metaActual: StateFlow<Meta?> get() = _metaActual

    private val _usuarioId = MutableStateFlow<Long?>(null)
    val usuarioId: StateFlow<Long?> get() = _usuarioId

    fun setUsuarioId(id: Long) {
        _usuarioId.value = id
    }

    fun cargarTotalIngresadoEnMeta(metaId: Long) {
        viewModelScope.launch {
            try {
                val total = repository.obtenerTotalIngresadoEnMeta(metaId)
                Log.d("MovimientoViewModel", "Total ingresado para meta $metaId: $total")
                _totalIngresadoMeta.value = total
            } catch (e: Exception) {
                Log.e("MovimientoViewModel", "Error al cargar total ingresado", e)
                _totalIngresadoMeta.value = 0.0
            }
        }
    }

    suspend fun obtenerTotalIngresadoEnMetaDirecto(metaId: Long): Double {
        return try {
            repository.obtenerTotalIngresadoEnMeta(metaId)
        } catch (e: Exception) {
            Log.e("MovimientoViewModel", "Error al obtener total directo", e)
            0.0
        }
    }

    fun actualizarMetaActual(meta: Meta) {
        _metaActual.value = meta
        meta.id?.let { cargarTotalIngresadoEnMeta(it) }
    }

    fun validarMontoMeta(montoIngresado: Double): Boolean {
        val meta = _metaActual.value ?: return false
        val totalIngresado = _totalIngresadoMeta.value ?: 0.0
        return (totalIngresado + montoIngresado) <= meta.montoTotal
    }

    fun guardarMovimiento(
        movimiento: Movimiento,
        usuarioId: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                if (movimiento.tipo == "IngresoMeta" && movimiento.presupuesto != null) {
                    val montoIngresado = movimiento.monto
                    if (!validarMontoMeta(montoIngresado)) {
                        onError("El monto excede el total permitido para esta meta")
                        return@launch
                    }
                }

                if (usuarioId <= 0) {
                    onError("Usuario no definido")
                    return@launch
                }

                val resultado = repository.guardarMovimiento(movimiento, usuarioId)
                if (resultado != null) {
                    onSuccess()
                } else {
                    onError("Error al guardar el movimiento")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.message ?: "Error desconocido"}")
            }
        }
    }
}
