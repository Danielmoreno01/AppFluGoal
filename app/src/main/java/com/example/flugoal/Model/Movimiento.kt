package com.example.flugoal.Model

data class Movimiento(
    val id: Long? = null,
    val tipo: String,
    val monto: Double,
    val fecha: String,
    val descripcion: String,
    val usuario: Usuario? = null,
    val presupuesto: Meta? = null
)
