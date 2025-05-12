package com.example.flugoal.Model

data class Avatar(
    val id: Long? = null,
    val estilo: String,
    val fondo: String,
    val color: String,
    val icono: String,
    val usuario: Usuario? = null
)