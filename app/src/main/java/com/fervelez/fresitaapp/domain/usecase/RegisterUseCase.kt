package com.fervelez.fresitaapp.domain.usecase

import com.fervelez.fresitaapp.domain.repository.AuthRepository
import retrofit2.Response

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(nombre: String, correo: String, password: String): Response<Map<String, String>> {
        return repository.register(nombre, correo, password)
    }
}
