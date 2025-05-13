package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Recompensa
import com.example.flugoal.Repository.RecompensaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecompensaViewModel : ViewModel() {

    private val repository = RecompensaRepository()

    private val _recompensas = MutableLiveData<List<Recompensa>?>(emptyList())
    val recompensas: MutableLiveData<List<Recompensa>?> = _recompensas

    fun obtenerRecompensas() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerRecompensas()
            }
            _recompensas.postValue(lista)
        }
    }

    fun guardarRecompensa(recompensa: Recompensa) {
        viewModelScope.launch {
            repository.guardarRecompensa(recompensa)
            obtenerRecompensas()
        }
    }

    fun eliminarRecompensa(id: Long) {
        viewModelScope.launch {
            repository.eliminarRecompensa(id)
            obtenerRecompensas()
        }
    }
}
