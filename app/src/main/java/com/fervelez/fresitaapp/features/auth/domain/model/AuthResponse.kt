package com.fervelez.fresitaapp.features.auth.domain.model

data class AuthResponse(
    val token: String,
    val usuario: User
)

