package com.example.flugoal.Repository

import com.example.flugoal.Interface.RetrofitClient
import com.example.flugoal.Model.Avatar

class AvatarRepository {

    suspend fun obtenerAvatares(): List<Avatar> {
        return RetrofitClient.apiService.obtenerAvatares()
    }

    suspend fun obtenerAvatar(id: Long): Avatar {
        return RetrofitClient.apiService.obtenerAvatar(id)
    }

    suspend fun guardarAvatar(avatar: Avatar): Avatar {
        return RetrofitClient.apiService.guardarAvatar(avatar)
    }

    suspend fun eliminarAvatar(id: Long) {
        RetrofitClient.apiService.eliminarAvatar(id)
    }
}
