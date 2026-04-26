package com.fervelez.fresitaapp.features.fruits.domain.usecase

import com.fervelez.fresitaapp.features.fruits.domain.repository.FruitRepository
import retrofit2.Response

class DeleteFruitUseCase(private val repository: FruitRepository) {
    suspend operator fun invoke(id: Int): Response<Map<String, Any>> {
        return repository.deleteFruit(id)
    }
}

