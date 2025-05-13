package com.example.flugoal.Model

import java.time.LocalDate

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val email: String,
    val contrasena: String,
    val fechaRegistro: String
)
