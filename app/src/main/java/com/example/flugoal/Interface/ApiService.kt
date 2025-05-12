package com.example.flugoal.Interface

import com.example.flugoal.Model.Avatar
import com.example.flugoal.Model.Meta
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {
    @GET("/api/avatares")
    suspend fun obtenerAvatares(): List<Avatar>

    @GET("/api/avatares/{id}")
    suspend fun obtenerAvatar(@Path("id") id: Long): Avatar

    @POST("/api/avatares")
    suspend fun guardarAvatar(@Body avatar: Avatar): Avatar

    @DELETE("/api/avatares/{id}")
    suspend fun eliminarAvatar(@Path("id") id: Long)

    @GET("/api/metas")
    suspend fun obtenerMetas(): List<Meta>

    @GET("/api/metas/{id}")
    suspend fun obtenerMeta(@Path("id") id: Long): Meta

    @POST("/api/metas")
    suspend fun guardarMeta(@Body meta: Meta): Meta

    @DELETE("/api/metas/{id}")
    suspend fun eliminarMeta(@Path("id") id: Long)

}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8081"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}