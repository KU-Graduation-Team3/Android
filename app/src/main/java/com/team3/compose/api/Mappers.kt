package com.team3.compose.api

import com.team3.compose.domain.model.SentenceSentiment
import com.team3.compose.network.PredictionResponse
import kotlin.collections.map

/**
 * API 응답 모델인 PredictionResponse를 UI에서 사용하는 도메인 모델 SentenceSentiment로 변환합니다.
 */
fun PredictionResponse.toDomainModel(): SentenceSentiment {
    val sentimentValue = when (this.label.lowercase()) {
        "positive" -> 1
        "negative" -> -1
        else -> 0
    }
    return SentenceSentiment(
        text = this.text,
        sentiment = sentimentValue
    )
}

/**
 * PredictionResponse 리스트 전체를 SentenceSentiment 리스트로 변환합니다.
 */
fun List<PredictionResponse>.toDomain(): List<SentenceSentiment> {
    return this.map { it.toDomainModel() }
}