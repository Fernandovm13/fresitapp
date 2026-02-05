package com.fervelez.fresitaapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fervelez.fresitaapp.model.Fruit
import com.fervelez.fresitaapp.repository.FruitRepository
import kotlinx.coroutines.launch
import java.io.File

class FruitViewModel(application: Application): AndroidViewModel(application) {
    private val repo = FruitRepository()
    val fruits = MutableLiveData<List<Fruit>>()
    val error = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun loadFruits() {
        loading.postValue(true)
        viewModelScope.launch {
            try {
                val resp = repo.getFruits()
                if (resp.isSuccessful) fruits.postValue(resp.body() ?: emptyList())
                else error.postValue(resp.message())
            } catch (e: Exception) {
                error.postValue(e.message)
            }
            loading.postValue(false)
        }
    }

    fun addFruit(nombre: String, nombreCientifico: String?, temporada: String?, clasificacion: String?, image: File?, usuarioId: Int, onDone: (Boolean,String?)->Unit) {
        viewModelScope.launch {
            try {
                val resp = repo.createFruit(nombre, nombreCientifico, temporada, clasificacion, image, usuarioId)
                if (resp.isSuccessful) onDone(true, null) else onDone(false, resp.message())
            } catch (e: Exception) {
                onDone(false, e.message)
            }
        }
    }
}
