package com.example.flugoal.Model

data class Recompensa(
    val id: Long? = null,
    val tipo: String,
    val valor: Int,
    val fecha: String,
    val usuario: Usuario? = null
)
