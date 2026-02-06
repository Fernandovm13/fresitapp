package com.fervelez.fresitaapp.api

import com.fervelez.fresitaapp.model.AuthResponse
import com.fervelez.fresitaapp.model.Fruit
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("/api/auth/register")
    suspend fun register(@Body body: Map<String, String>): Response<Map<String, String>>

    @POST("/api/auth/login")
    suspend fun login(@Body body: Map<String, String>): Response<AuthResponse>

    @GET("/api/frutas")
    suspend fun getFruits(): Response<List<Fruit>>

    @Multipart
    @POST("/api/frutas")
    suspend fun createFruit(
        @Part("nombre") nombre: RequestBody,
        @Part("nombre_cientifico") nombreCientifico: RequestBody?,
        @Part("temporada") temporada: RequestBody?,
        @Part("clasificacion") clasificacion: RequestBody?,
        @Part imagen: MultipartBody.Part?,
        @Part("usuario_id") usuarioId: RequestBody
    ): Response<Map<String, Any>>

    @PUT("/api/frutas/{id}")
    suspend fun updateFruit(
        @Path("id") id: Int,
        @Body body: Map<String, String?>
    ): Response<Map<String, Any>>

    @DELETE("/api/frutas/{id}")
    suspend fun deleteFruit(@Path("id") id: Int): Response<Map<String, Any>>
}