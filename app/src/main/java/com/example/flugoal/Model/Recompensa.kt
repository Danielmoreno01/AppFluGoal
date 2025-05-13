package com.example.flugoal.Model

data class Recompensa(
    val id: Long? = null,
    val tipo: String,
    val valor: Int,
    val fecha: String, // formato ISO 8601: "yyyy-MM-dd"
    val usuario: Usuario? = null
)
