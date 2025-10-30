package com.team3.compose.network

import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.POST

@JsonClass(generateAdapter = true)
data class PredictionRequest(
    val texts: List<String>
)

@JsonClass(generateAdapter = true)
data class PredictionResponse(
    val text: String,
    val label: String,
    val score: Double
)

interface PredictionApiService {
    @POST("predict")
    suspend fun getPrediction(
        @Body request: PredictionRequest
    ): List<PredictionResponse>
}