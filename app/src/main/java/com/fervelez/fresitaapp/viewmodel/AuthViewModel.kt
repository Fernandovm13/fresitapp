package com.fervelez.fresitaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.example.frutasapp.repository.AuthRepository
import kotlinx.coroutines.Dispatchers

class AuthViewModel(application: Application): AndroidViewModel(application) {
    private val repository = AuthRepository()

    fun login(correo: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.loading())
        try {
            val resp = repository.login(correo, password)
            if (resp.isSuccessful) emit(Result.success(resp.body())) else emit(Result.error(resp.errorBody()?.string() ?: resp.message()))
        } catch (e: Exception) { emit(Result.error(e.message)) }
    }

    fun register(nombre: String, correo: String, password: String) = liveData(Dispatchers.IO) {
        emit(Result.loading())
        try {
            val resp = repository.register(nombre, correo, password)
            if (resp.isSuccessful) emit(Result.success(resp.body())) else emit(Result.error(resp.errorBody()?.string() ?: resp.message()))
        } catch (e: Exception) { emit(Result.error(e.message)) }
    }
}
