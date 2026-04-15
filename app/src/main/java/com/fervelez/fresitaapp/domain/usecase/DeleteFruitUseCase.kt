package com.fervelez.fresitaapp.domain.usecase

import com.fervelez.fresitaapp.domain.repository.FruitRepository
import retrofit2.Response

class DeleteFruitUseCase(private val repository: FruitRepository) {
    suspend operator fun invoke(id: Int): Response<Map<String, Any>> {
        return repository.deleteFruit(id)
    }
}
