package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Meta
import com.example.flugoal.Repository.MetaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MetaViewModel : ViewModel() {

    private val repository = MetaRepository()

    private val _metas = MutableLiveData<List<Meta>?>(emptyList())
    val metas: MutableLiveData<List<Meta>?> = _metas

    fun obtenerMetas() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerMetas()
            }
            _metas.postValue(lista)
        }
    }

    fun guardarMeta(meta: Meta) {
        viewModelScope.launch {
            repository.guardarMeta(meta)
            obtenerMetas()
        }
    }

    fun eliminarMeta(id: Long) {
        viewModelScope.launch {
            repository.eliminarMeta(id)
            obtenerMetas()
        }
    }
}
