package com.team3.compose.repository

import com.team3.compose.api.toDomain
import com.team3.compose.api.toDomainModel
import com.team3.compose.domain.model.SentenceSentiment
import com.team3.compose.network.PredictionApiService
import com.team3.compose.network.PredictionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class PredictionRepository @Inject constructor(
    private val apiService: PredictionApiService
) {

    /**
     * 입력된 텍스트 리스트에 대한 감성 분석을 서버에 요청하고,
     * 결과를 앱의 도메인 모델인 List<SentenceSentiment>로 변환하여 반환합니다.
     * @param texts 분석할 문장 리스트
     * @return Result<List<SentenceSentiment>> 성공 시 감성 분석 결과 리스트, 실패 시 Exception
     */
    suspend fun fetchPrediction(texts: List<String>): Result<List<SentenceSentiment>> {
        return withContext(Dispatchers.IO) {
            try {
                val request = PredictionRequest(texts = texts)
                val response = apiService.getPrediction(request)
                // API 응답을 도메인 모델로 매핑하여 성공 결과 반환
                Result.success(response.toDomain())
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure(e)
            }
        }
    }
}