package com.example.marsphotos.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

const val ESP32_BASE_URL = "http://192.168.4.1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(ESP32_BASE_URL)
    .build()

object Esp32Api {
    val retrofitService: Esp32ApiService by lazy {
        retrofit.create(Esp32ApiService::class.java)
    }
}
