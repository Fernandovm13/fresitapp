package com.fervelez.fresitaapp.data.repository

import com.fervelez.fresitaapp.data.network.RetrofitClient
import com.fervelez.fresitaapp.domain.model.Fruit
import com.fervelez.fresitaapp.domain.repository.FruitRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class FruitRepositoryImpl : FruitRepository {
    private val api = RetrofitClient.api

    override suspend fun getFruits(): Response<List<Fruit>> = api.getFruits()

    override suspend fun createFruit(
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        imageFile: File?,
        usuarioId: Int
    ): Response<Map<String, Any>> {
        val nombreBody = RequestBody.create("text/plain".toMediaTypeOrNull(), nombre)
        val cientificoBody = nombreCientifico?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val temporadaBody = temporada?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val clasBody = clasificacion?.let { RequestBody.create("text/plain".toMediaTypeOrNull(), it) }
        val userBody = RequestBody.create("text/plain".toMediaTypeOrNull(), usuarioId.toString())

        val imagenPart = imageFile?.let {
            val reqFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
            MultipartBody.Part.createFormData("imagen", it.name, reqFile)
        }

        return api.createFruit(nombreBody, cientificoBody, temporadaBody, clasBody, imagenPart, userBody)
    }

    override suspend fun updateFruit(
        id: Int,
        nombre: String,
        nombreCientifico: String?,
        temporada: String?,
        clasificacion: String?,
        imageFile: File?
    ): Response<Map<String, Any>> {
        val body = mapOf(
            "nombre" to nombre,
            "nombre_cientifico" to nombreCientifico,
            "temporada" to temporada,
            "clasificacion" to clasificacion
        )
        return api.updateFruit(id, body)
    }

    override suspend fun deleteFruit(id: Int): Response<Map<String, Any>> = api.deleteFruit(id)
}