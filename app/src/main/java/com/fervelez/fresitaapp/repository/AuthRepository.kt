package com.fervelez.fresitaapp.repository

import com.fervelez.fresitaapp.api.RetrofitClient
import retrofit2.Response

class AuthRepository {
    private val api = RetrofitClient.api

    suspend fun register(nombre: String, correo: String, password: String): Response<Map<String, String>> {
        return api.register(mapOf("nombre_completo" to nombre, "correo" to correo, "password" to password))
    }

    suspend fun login(correo: String, password: String) = api.login(mapOf("correo" to correo, "password" to password))
}
