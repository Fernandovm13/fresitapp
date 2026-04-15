package com.fervelez.fresitaapp.domain.usecase

import com.fervelez.fresitaapp.domain.repository.FruitRepository
import retrofit2.Response
import java.io.File

class AddFruitUseCase(private val repository: FruitRepository) {
    suspend operator fun invoke(
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        image: File?,
        usuarioId: Int
    ): Response<Map<String, Any>> {
        return repository.createFruit(nombre, nombreCientifico, temporada, clasificacion, image, usuarioId)
    }
}
