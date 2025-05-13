package com.example.flugoal.Model

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val email: String,
    val contrasena: String,
    val fechaRegistro: String
)
