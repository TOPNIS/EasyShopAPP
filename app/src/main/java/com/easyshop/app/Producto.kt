package com.easyshop.app

data class Producto(
    val id: String,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val descripcion: String,
    val especificaciones: String,
    val imagenResId: Int = 0
)
