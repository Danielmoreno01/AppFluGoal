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

    private val _historialMovimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val historialMovimientos: StateFlow<List<Movimiento>> get() = _historialMovimientos

    private val _egresosMovimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val egresosMovimientos: StateFlow<List<Movimiento>> get() = _egresosMovimientos

    private val _ingresosMovimientos = MutableStateFlow<List<Movimiento>>(emptyList())
    val ingresosMovimientos: StateFlow<List<Movimiento>> get() = _ingresosMovimientos

    private val _movimientoSeleccionado = MutableStateFlow<Movimiento?>(null)
    val movimientoSeleccionado: StateFlow<Movimiento?> get() = _movimientoSeleccionado

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

    fun cargarHistorialMovimientos(usuarioId: String) {
        viewModelScope.launch {
            try {
                val idLong = usuarioId.toLongOrNull()
                if (idLong != null) {
                    val historial = repository.obtenerHistorialMovimientos(idLong)
                    _historialMovimientos.value = historial
                } else {
                    Log.e("MovimientoViewModel", "ID inválido")
                }
            } catch (e: Exception) {
                Log.e("MovimientoViewModel", "Error al cargar historial", e)
                _historialMovimientos.value = emptyList()
            }
        }
    }

    fun cargarEgresosPorUsuario(usuarioId: String) {
        viewModelScope.launch {
            try {
                val idLong = usuarioId.toLongOrNull()
                if (idLong != null) {
                    val egresos = repository.obtenerEgresosPorUsuario(idLong)
                    _egresosMovimientos.value = egresos
                } else {
                    Log.e("MovimientoViewModel", "ID inválido para cargar egresos")
                    _egresosMovimientos.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("MovimientoViewModel", "Error al cargar egresos", e)
                _egresosMovimientos.value = emptyList()
            }
        }
    }

    fun cargarIngresosPorUsuario(usuarioId: String) {
        viewModelScope.launch {
            try {
                val idLong = usuarioId.toLongOrNull()
                if (idLong != null) {
                    val ingresos = repository.obtenerIngresosPorUsuario(idLong)
                    _ingresosMovimientos.value = ingresos
                } else {
                    Log.e("MovimientoViewModel", "ID inválido para cargar ingresos")
                    _ingresosMovimientos.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("MovimientoViewModel", "Error al cargar ingresos", e)
                _ingresosMovimientos.value = emptyList()
            }
        }
    }

    fun actualizarMovimiento(
        movimientoId: Int,
        movimiento: Movimiento,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val resultado = repository.actualizarMovimiento(movimientoId, movimiento)
                if (resultado != null) {
                    onSuccess()
                } else {
                    onError("Error al actualizar el movimiento")
                }
            } catch (e: Exception) {
                onError("Excepción al actualizar: ${e.message ?: "Error desconocido"}")
            }
        }
    }

    fun eliminarMovimiento(
        movimientoId: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val exito = repository.eliminarMovimiento(movimientoId)
                if (exito) {
                    onSuccess()
                } else {
                    onError("Error al eliminar el movimiento")
                }
            } catch (e: Exception) {
                onError("Excepción al eliminar: ${e.message ?: "Error desconocido"}")
            }
        }
    }

    fun cargarMovimientoPorId(movimientoId: Int) {
        viewModelScope.launch {
            try {
                val movimiento = repository.obtenerMovimientoPorId(movimientoId)
                if (movimiento != null) {
                    _movimientoSeleccionado.value = movimiento
                } else {
                    Log.e("MovimientoViewModel", "No se encontró movimiento con ID $movimientoId")
                    _movimientoSeleccionado.value = null
                }
            } catch (e: Exception) {
                Log.e("MovimientoViewModel", "Error al cargar movimiento por ID", e)
                _movimientoSeleccionado.value = null
            }
        }
    }

}

