package com.unsa.idnp.listsample

data class Curso(
    val id: Int,
    var nombre: String, // Lo ponemos como 'var' por si quieres mutarlo, aunque 'copy' es mejor
    val descripcion: String
)