package com.fervelez.fresitaapp.features.auth.domain.usecase

import com.fervelez.fresitaapp.features.auth.domain.model.AuthResponse
import com.fervelez.fresitaapp.features.auth.domain.repository.AuthRepository
import retrofit2.Response

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(correo: String, password: String): Response<AuthResponse> {
        return repository.login(correo, password)
    }
}

