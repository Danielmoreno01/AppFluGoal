package com.example.flugoal.Interface

import com.example.flugoal.Model.Avatar
import com.example.flugoal.Model.Meta
import com.example.flugoal.Model.Movimiento
import com.example.flugoal.Model.Recompensa
import com.example.flugoal.Model.Tarea
import com.example.flugoal.Model.TiendaItem
import com.example.flugoal.Model.Usuario
import retrofit2.Response
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

    @GET("/api/movimientos")
    suspend fun obtenerMovimientos(): List<Movimiento>

    @GET("/api/movimientos/{id}")
    suspend fun obtenerMovimiento(@Path("id") id: Long): Movimiento

    @POST("/api/movimientos")
    suspend fun guardarMovimiento(@Body movimiento: Movimiento): Movimiento

    @DELETE("/api/movimientos/{id}")
    suspend fun eliminarMovimiento(@Path("id") id: Long)

    @GET("/api/recompensas")
    suspend fun obtenerRecompensas(): List<Recompensa>

    @GET("/api/recompensas/{id}")
    suspend fun obtenerRecompensa(@Path("id") id: Long): Recompensa

    @POST("/api/recompensas")
    suspend fun guardarRecompensa(@Body recompensa: Recompensa): Recompensa

    @DELETE("/api/recompensas/{id}")
    suspend fun eliminarRecompensa(@Path("id") id: Long)

    @GET("/api/tareas")
    suspend fun obtenerTareas(): List<Tarea>

    @GET("/api/tareas/{id}")
    suspend fun obtenerTarea(@Path("id") id: Long): Tarea

    @POST("/api/tareas")
    suspend fun guardarTarea(@Body tarea: Tarea): Tarea

    @DELETE("/api/tareas/{id}")
    suspend fun eliminarTarea(@Path("id") id: Long)

    @GET("/api/tienda-items")
    suspend fun obtenerItemsTienda(): List<TiendaItem>

    @GET("/api/tienda-items/{id}")
    suspend fun obtenerItemTienda(@Path("id") id: Long): TiendaItem

    @POST("/api/tienda-items")
    suspend fun guardarItemTienda(@Body item: TiendaItem): TiendaItem

    @DELETE("/api/tienda-items/{id}")
    suspend fun eliminarItemTienda(@Path("id") id: Long)

    @GET("/api/usuarios/correo/{correo}")
    suspend fun verificarCorreoExistente(@Path("correo") correo: String): Response<Boolean>

    @POST("/api/usuarios")
    suspend fun guardarUsuario(@Body usuario: Usuario): Usuario
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}