package com.fervelez.fresitaapp.domain.usecase

import com.fervelez.fresitaapp.domain.model.AuthResponse
import com.fervelez.fresitaapp.domain.repository.AuthRepository
import retrofit2.Response

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(correo: String, password: String): Response<AuthResponse> {
        return repository.login(correo, password)
    }
}
