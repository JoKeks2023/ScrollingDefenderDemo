package com.example.marsphotos.network

import kotlinx.serialization.Serializable
import retrofit2.http.GET

@Serializable
data class StatusResponse(val defaultResponse: Boolean)

interface Esp32ApiService {
    @GET("status")
    suspend fun getStatus(): StatusResponse
}
