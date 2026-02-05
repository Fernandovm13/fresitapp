package com.fervelez.fresitaapp.model

data class Fruit(
    val id: Int,
    val nombre: String?,
    val nombre_cientifico: String?,
    val temporada: String?,
    val clasificacion: String?,
    val imagen_path: String?,
    val usuario_id: Int?
)
