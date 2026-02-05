package com.fervelez.fresitaapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // IMPORTANTE: reemplaza <TU_IP_LOCAL> por la IP de tu PC en la red (ej. 192.168.1.5)
    // Ejemplo final: private const val BASE_URL = "http://192.168.1.5:3000/"
    private const val BASE_URL = "http://<TU_IP_LOCAL>:3000/"

    private val client by lazy {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
