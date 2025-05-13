package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Tarea
import com.example.flugoal.Repository.TareaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TareaViewModel : ViewModel() {

    private val repository = TareaRepository()

    private val _tareas = MutableLiveData<List<Tarea>?>(emptyList())
    val tareas: MutableLiveData<List<Tarea>?> = _tareas

    fun obtenerTareas() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerTareas()
            }
            _tareas.postValue(lista)
        }
    }

    fun guardarTarea(tarea: Tarea) {
        viewModelScope.launch {
            repository.guardarTarea(tarea)
            obtenerTareas()
        }
    }

    fun eliminarTarea(id: Long) {
        viewModelScope.launch {
            repository.eliminarTarea(id)
            obtenerTareas()
        }
    }
}
