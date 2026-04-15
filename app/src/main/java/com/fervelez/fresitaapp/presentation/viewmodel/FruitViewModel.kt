package com.fervelez.fresitaapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fervelez.fresitaapp.data.repository.FruitRepositoryImpl
import com.fervelez.fresitaapp.domain.model.Fruit
import com.fervelez.fresitaapp.domain.usecase.*
import kotlinx.coroutines.launch
import java.io.File

class FruitViewModel(application: Application): AndroidViewModel(application) {
    // En una app real con DI, esto se inyectaría.
    private val repository = FruitRepositoryImpl()
    private val getFruitsUseCase = GetFruitsUseCase(repository)
    private val addFruitUseCase = AddFruitUseCase(repository)
    private val updateFruitUseCase = UpdateFruitUseCase(repository)
    private val deleteFruitUseCase = DeleteFruitUseCase(repository)

    val fruits = MutableLiveData<List<Fruit>>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun loadFruits() {
        loading.postValue(true)
        viewModelScope.launch {
            try {
                val resp = getFruitsUseCase()
                if (resp.isSuccessful) fruits.postValue(resp.body() ?: emptyList())
                else error.postValue(resp.message())
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            loading.postValue(false)
        }
    }

    fun addFruit(nombre: String, nombreCientifico: String?, temporada: String?, clasificacion: String?, image: File?, usuarioId: Int, onDone: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val resp = addFruitUseCase(nombre, nombreCientifico, temporada, clasificacion, image, usuarioId)
                if (resp.isSuccessful) onDone(true, null) else onDone(false, resp.message())
            } catch (e: Exception) {
                onDone(false, e.message)
            }
        }
    }

    fun updateFruit(id: Int, nombre: String, nombreCientifico: String?, temporada: String?, clasificacion: String?, image: File?, onDone: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val resp = updateFruitUseCase(id, nombre, nombreCientifico, temporada, clasificacion, image)
                if (resp.isSuccessful) onDone(true, null)
                else onDone(false, "Error: ${resp.code()}")
            } catch (e: Exception) {
                onDone(false, e.message)
            }
        }
    }

    fun deleteFruit(id: Int, onDone: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val resp = deleteFruitUseCase(id)
                if (resp.isSuccessful) onDone(true, null) else onDone(false, resp.message())
            } catch (e: Exception) {
                onDone(false, e.message)
            }
        }
    }
}