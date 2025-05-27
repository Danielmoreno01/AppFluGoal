package com.example.flugoal.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Meta
import com.example.flugoal.Repository.MetaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MetaViewModel : ViewModel() {

    private val repository = MetaRepository()

    private val _metas = MutableStateFlow<List<Meta>>(emptyList())
    val metas: StateFlow<List<Meta>> = _metas

    public val _metaSeleccionada = MutableStateFlow<Meta?>(null)
    val metaSeleccionada: StateFlow<Meta?> = _metaSeleccionada

    fun cargarMetasPorUsuario(usuarioId: String) {
        viewModelScope.launch {
            try {
                val metasUsuario = repository.obtenerMetasPorUsuario(usuarioId.toLong())
                _metas.value = metasUsuario ?: emptyList()
            } catch (e: Exception) {
                Log.e("MetaViewModel", "Error al cargar metas: ${e.message}")
                _metas.value = emptyList()
            }
        }
    }

    fun guardarMeta(meta: Meta, usuarioId: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val success = repository.guardarMeta(meta, usuarioId)
                if (success) {
                    onSuccess()
                } else {
                    onError("No se pudo guardar la meta.")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.message}")
            }
        }
    }

    fun cargarMetaPorId(metaId: Long, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val meta = repository.obtenerMetaPorId(metaId)
                _metaSeleccionada.value = meta
            } catch (e: Exception) {
                Log.e("MetaViewModel", "Error al cargar meta por ID: ${e.message}")
                onError("No se pudo cargar la meta.")
            }
        }
    }

    fun actualizarMeta(metaId: Long, meta: Meta, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val success = repository.actualizarMeta(metaId, meta)
                if (success) {
                    onSuccess()
                } else {
                    onError("No se pudo actualizar la meta.")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.message}")
            }
        }
    }

    fun eliminarMeta(metaId: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val success = repository.eliminarMeta(metaId)
                if (success) {
                    onSuccess()
                } else {
                    onError("No se pudo eliminar la meta.")
                }
            } catch (e: Exception) {
                onError("Excepción: ${e.message}")
            }
        }
    }
}
