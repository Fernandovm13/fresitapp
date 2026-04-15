package com.fervelez.fresitaapp.domain.usecase

import com.fervelez.fresitaapp.domain.model.Fruit
import com.fervelez.fresitaapp.domain.repository.FruitRepository
import retrofit2.Response

class GetFruitsUseCase(private val repository: FruitRepository) {
    suspend operator fun invoke(): Response<List<Fruit>> {
        return repository.getFruits()
    }
}
