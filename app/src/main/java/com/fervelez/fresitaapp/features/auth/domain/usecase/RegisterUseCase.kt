package com.fervelez.fresitaapp.features.auth.domain.usecase

import com.fervelez.fresitaapp.features.auth.domain.repository.AuthRepository
import retrofit2.Response

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(nombre: String, correo: String, password: String): Response<Map<String, String>> {
        return repository.register(nombre, correo, password)
    }
}

