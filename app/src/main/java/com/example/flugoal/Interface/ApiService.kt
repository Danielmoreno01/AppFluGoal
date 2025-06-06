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

    //Verificar correo al registrar
    @GET("/api/usuarios/correo/{correo}")
    suspend fun verificarCorreo(@Path("correo") correo: String): Response<Boolean>

    //registrar
    @POST("/api/usuarios")
    suspend fun guardarUsuario(@Body usuario: Usuario)

    //obtenerid
    @GET("/api/usuarios/email/{email}")
    suspend fun obtenerUsuarioPorEmail(@Path("email") email: String): Usuario?

    //obtenernombreporid
    data class NombreResponse(val nombre: String)
    @GET("/api/usuarios/{id}/nombre")
    suspend fun obtenerNombreUsuario(@Path("id") id: Long): NombreResponse

    //cargarmetas
    @GET("/api/metas/usuario/{usuarioId}")
    suspend fun obtenerMetasPorUsuario(@Path("usuarioId") usuarioId: Long): Response<List<Meta>>

    //guardarmeta
    @POST("/api/metas/usuario/{usuarioId}")
    suspend fun guardarMeta(
        @Path("usuarioId") usuarioId: Long,
        @Body meta: Meta
    ): Response<Meta>

    //cargartotalahorroparaunameta
    @GET("/api/movimientos/metas/{metaId}/ingresosMeta")
    suspend fun obtenerTotalIngresadoEnMeta(@Path("metaId") metaId: Long): Double

    //guardarmovimiento
    @POST("/api/movimientos/usuario/{usuarioId}")
    suspend fun guardarMovimiento(
        @Path("usuarioId") usuarioId: Long,
        @Body movimiento: Movimiento
    ): Response<Movimiento>

    //cuerpometaporid
    @GET("/api/metas/{id}")
    suspend fun obtenerMetaPorId(@Path("id") id: Long): Response<Meta>

    // Actualizar meta
    @PUT("/api/metas/{id}")
    suspend fun actualizarMeta(
        @Path("id") id: Long,
        @Body meta: Meta
    ): Response<Meta>

    // Eliminar meta
    @DELETE("/api/metas/{id}")
    suspend fun eliminarMeta(@Path("id") id: Long): Response<Unit>

    // Obtener historial de movimientos por usuario ordenados por fecha
    @GET("/api/movimientos/usuario/{usuarioId}/historial")
    suspend fun obtenerHistorialMovimientos(@Path("usuarioId") usuarioId: Long): Response<List<Movimiento>>

    //Obtener egresos
    @GET("/api/movimientos/usuario/{usuarioId}/egresos")
    suspend fun obtenerEgresosPorUsuario(@Path("usuarioId") usuarioId: Long): Response<List<Movimiento>>

    //Obtener ingresos
    @GET("/api/movimientos/usuario/{usuarioId}/ingresos")
    suspend fun obtenerIngresosPorUsuario(@Path("usuarioId") usuarioId: Long): Response<List<Movimiento>>

    // Obtener ingresos destinados a metas por usuario
    @GET("/api/movimientos/usuario/{usuarioId}/ingresos_metas")
    suspend fun obtenerIngresosMetasPorUsuario(@Path("usuarioId") usuarioId: Long): Response<List<Movimiento>>

    // Obtener ahorros por usuario
    @GET("/api/movimientos/usuario/{usuarioId}/ahorros")
    suspend fun obtenerAhorrosPorUsuario(@Path("usuarioId") usuarioId: Long): Response<List<Movimiento>>

    // Actualizar un movimiento
    @PUT("/api/movimientos/{movimientoId}")
    suspend fun actualizarMovimiento(
        @Path("movimientoId") movimientoId: Int,
        @Body movimiento: Movimiento
    ): Response<Movimiento>

    // Eliminar un movimiento
    @DELETE("/api/movimientos/{movimientoId}")
    suspend fun eliminarMovimiento(@Path("movimientoId") movimientoId: Int): Response<Unit>

    // Obtener movimiento por ID
    @GET("/api/movimientos/{movimientoId}")
    suspend fun obtenerMovimientoPorId(@Path("movimientoId") movimientoId: Int): Response<Movimiento>

}

object RetrofitClient {
    private const val BASE_URL = "https://apiflugoal.onrender.com/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
