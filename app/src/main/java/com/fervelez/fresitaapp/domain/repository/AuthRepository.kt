package com.fervelez.fresitaapp.domain.repository

import com.fervelez.fresitaapp.domain.model.AuthResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun register(nombre: String, correo: String, password: String): Response<Map<String, String>>
    suspend fun login(correo: String, password: String): Response<AuthResponse>
}
