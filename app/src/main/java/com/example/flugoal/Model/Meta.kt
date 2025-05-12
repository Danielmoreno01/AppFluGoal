package com.example.flugoal.Model

data class Meta(
    val id: Long? = null,
    val nombre: String,
    val montoTotal: Double,
    val fechaInicio: String, // ISO 8601 (yyyy-MM-dd)
    val fechaFin: String,
    val usuario: Usuario? = null,
    val movimientos: List<Movimiento>? = null
)
