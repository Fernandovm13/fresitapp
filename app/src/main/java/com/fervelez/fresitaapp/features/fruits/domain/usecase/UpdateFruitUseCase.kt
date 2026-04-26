package com.fervelez.fresitaapp.features.fruits.domain.usecase

import com.fervelez.fresitaapp.features.fruits.domain.repository.FruitRepository
import retrofit2.Response
import java.io.File

class UpdateFruitUseCase(private val repository: FruitRepository) {
    suspend operator fun invoke(
        id: Int,
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        image: File?
    ): Response<Map<String, Any>> {
        return repository.updateFruit(id, nombre, nombreCientifico, temporada, clasificacion, image)
    }
}

