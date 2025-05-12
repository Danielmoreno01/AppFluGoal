package com.example.flugoal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flugoal.Model.Avatar
import com.example.flugoal.Repository.AvatarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AvatarViewModel : ViewModel() {

    private val repository = AvatarRepository()

    private val _avatares = MutableLiveData<List<Avatar>?>(emptyList())
    val avatares: MutableLiveData<List<Avatar>?> = _avatares

    fun obtenerAvatares() {
        viewModelScope.launch {
            val lista = withContext(Dispatchers.IO) {
                repository.obtenerAvatares()
            }
            _avatares.postValue(lista)
        }
    }

    fun guardarAvatar(avatar: Avatar) {
        viewModelScope.launch {
            repository.guardarAvatar(avatar)
            obtenerAvatares()
        }
    }

    fun eliminarAvatar(id: Long) {
        viewModelScope.launch {
            repository.eliminarAvatar(id)
            obtenerAvatares()
        }
    }
}
