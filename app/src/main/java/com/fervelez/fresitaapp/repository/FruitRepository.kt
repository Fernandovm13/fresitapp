package com.fervelez.fresitaapp.repository

import com.fervelez.fresitaapp.api.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class FruitRepository {
    private val api = RetrofitClient.api

    suspend fun getFruits() = api.getFruits()

    suspend fun createFruit(
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        imageFile: File?,
        usuarioId: Int
    ) = run {
        val nombreBody = RequestBody.create("text/plain".toMediaTypeOrNull(), nombre)
        val cientificoBody = nombreCientifico?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val temporadaBody = temporada?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val clasBody = clasificacion?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val userBody = RequestBody.create("text/plain".toMediaTypeOrNull(), usuarioId.toString())

        val imagenPart = imageFile?.let {
            val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
            MultipartBody.Part.createFormData("imagen", it.name, reqFile)
        }

        api.createFruit(nombreBody, cientificoBody, temporadaBody, clasBody, imagenPart, userBody)
    }
}
