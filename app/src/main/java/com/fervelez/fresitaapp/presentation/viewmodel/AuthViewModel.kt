package com.fervelez.fresitaapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.fervelez.fresitaapp.data.repository.AuthRepositoryImpl
import com.fervelez.fresitaapp.domain.usecase.LoginUseCase
import com.fervelez.fresitaapp.domain.usecase.RegisterUseCase
import kotlinx.coroutines.Dispatchers

class AuthViewModel(application: Application): AndroidViewModel(application) {
    // En una app real con DI, esto se inyectaría.
    private val repository = AuthRepositoryImpl()
    private val loginUseCase = LoginUseCase(repository)
    private val registerUseCase = RegisterUseCase(repository)

    fun login(correo: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.loading())
        try {
            val resp = loginUseCase(correo, password)
            if (resp.isSuccessful) emit(Result.success(resp.body()))
            else emit(Result.error(resp.errorBody()?.string() ?: resp.message()))
        } catch (e: Exception) {
            emit(Result.error(e.message))
        }
    }

    fun register(nombre: String, correo: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.loading())
        try {
            val resp = registerUseCase(nombre, correo, password)
            if (resp.isSuccessful) emit(Result.success(resp.body()))
            else emit(Result.error(resp.errorBody()?.string() ?: resp.message()))
        } catch (e: Exception) {
            emit(Result.error(e.message))
        }
    }
}
