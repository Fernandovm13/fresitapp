package com.fervelez.fresitaapp.domain.model

data class AuthResponse(
    val token: String,
    val usuario: User
)
