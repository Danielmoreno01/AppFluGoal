package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.Repository.MovimientoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovimientoViewModel : ViewModel() {

    private val repository = MovimientoRepository()

    private val _movimientos = MutableLiveData<List<Movimiento>?>(emptyList())
    val movimientos: MutableLiveData<List<Movimiento>?> = _movimientos

    fun obtenerMovimientos() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerMovimientos()
            }
            _movimientos.postValue(lista)
        }
    }

    fun guardarMovimiento(movimiento: Movimiento) {
        viewModelScope.launch {
            repository.guardarMovimiento(movimiento)
            obtenerMovimientos()
        }
    }

    fun eliminarMovimiento(id: Long) {
        viewModelScope.launch {
            repository.eliminarMovimiento(id)
            obtenerMovimientos()
        }
    }
}
