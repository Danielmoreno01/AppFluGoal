package com.example.flugoal.Model

data class Tarea(
    val id: Long? = null,
    val titulo: String,
    val descripcion: String,
    val fechaCreacion: String,
    val completada: Boolean,
    val usuario: Usuario? = null
)
