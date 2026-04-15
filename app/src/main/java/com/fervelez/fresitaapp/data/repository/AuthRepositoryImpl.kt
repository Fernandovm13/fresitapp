package com.fervelez.fresitaapp.data.repository

import com.fervelez.fresitaapp.data.network.RetrofitClient
import com.fervelez.fresitaapp.domain.model.AuthResponse
import com.fervelez.fresitaapp.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl : AuthRepository {
    private val api = RetrofitClient.api

    override suspend fun register(nombre: String, correo: String, password: String): Response<Map<String, String>> {
        return api.register(mapOf("nombre_completo" to nombre, "correo" to correo, "password" to password))
    }

    override suspend fun login(correo: String, password: String): Response<AuthResponse> {
        return api.login(mapOf("correo" to correo, "password" to password))
    }
}
