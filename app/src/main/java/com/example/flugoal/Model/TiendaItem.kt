package com.example.flugoal.Model

data class TiendaItem(
    val id: Long? = null,
    val nombre: String,
    val tipo: String,  // fondo / icono / tema
    val precio: Int
)
