package com.fervelez.fresitaapp.features.auth.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.fervelez.fresitaapp.features.auth.data.repository.AuthRepositoryImpl
import com.fervelez.fresitaapp.features.auth.domain.usecase.LoginUseCase
import com.fervelez.fresitaapp.features.auth.domain.usecase.RegisterUseCase
import com.fervelez.fresitaapp.core.util.Result
import kotlinx.coroutines.Dispatchers

class AuthViewModel(application: Application): AndroidViewModel(application) {
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

