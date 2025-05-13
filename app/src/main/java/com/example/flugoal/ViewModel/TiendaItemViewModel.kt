package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.TiendaItem
import com.example.flugoal.Repository.TiendaItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TiendaItemViewModel : ViewModel() {

    private val repository = TiendaItemRepository()

    private val _items = MutableLiveData<List<TiendaItem>?>(emptyList())
    val items: MutableLiveData<List<TiendaItem>?> = _items

    fun obtenerItems() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerItemsTienda()
            }
            _items.postValue(lista)
        }
    }

    fun guardarItem(item: TiendaItem) {
        viewModelScope.launch {
            repository.guardarItemTienda(item)
            obtenerItems()
        }
    }

    fun eliminarItem(id: Long) {
        viewModelScope.launch {
            repository.eliminarItemTienda(id)
            obtenerItems()
        }
    }
}
