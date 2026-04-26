package com.fervelez.fresitaapp.features.fruits.domain.repository

import com.fervelez.fresitaapp.features.fruits.domain.model.Fruit
import retrofit2.Response
import java.io.File

interface FruitRepository {
    suspend fun getFruits(): Response<List<Fruit>>
    suspend fun createFruit(
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        imageFile: File?,
        usuarioId: Int
    ): Response<Map<String, Any>>
    suspend fun updateFruit(
        id: Int,
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        imageFile: File?
    ): Response<Map<String, Any>>
    suspend fun deleteFruit(id: Int): Response<Map<String, Any>>
}

