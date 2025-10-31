package com.team3.compose.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team3.compose.data.MockData
import com.team3.compose.repository.PredictionRepository
import com.team3.compose.ui.Argument
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val predictionRepository: PredictionRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val stockCode: String = savedStateHandle[Argument.STOCK_CODE] ?: "005930"
    var uiState by mutableStateOf(DetailsUiState())
        private set


    init {
        loadStockDetails()
        fetchOverallNewsSentiment()
    }

    private fun loadStockDetails() {
        uiState = uiState.copy(isLoading = true)

        val stock = MockData.stockList.find { it.code == stockCode }
        val sentiment = MockData.sentimentScores[stockCode]
        val history = MockData.stockPriceHistory
        val news = MockData.newsArticles.filter { it.stockCode == stockCode }

        uiState = uiState.copy(
            stock = stock,
            sentimentScore = sentiment,
            stockPriceHistory = history,
            newsArticles = news,
            isLoading = false
        )
    }


    private fun fetchOverallNewsSentiment() {
        viewModelScope.launch {
            uiState = uiState.copy(isOverallSentimentLoading = true)
            val newsTitles = uiState.newsArticles.map { it.title }
            if (newsTitles.isNotEmpty()) {
                predictionRepository.fetchPrediction(listOf("005930"))
                    .onSuccess {
                        val positiveCount = it.count { sentence -> sentence.sentiment == 1 }
                        val neutralCount = it.count { sentence -> sentence.sentiment == 0 }
                        val negativeCount = it.count { sentence -> sentence.sentiment == -1 }

                        val overallSentiment = when (listOf(positiveCount, neutralCount, negativeCount).maxOrNull()) {
                            positiveCount -> "긍정"
                            neutralCount -> "중립"
                            negativeCount -> "부정"
                            else -> "알 수 없음"
                        }
                        uiState = uiState.copy(
                            overallNewsSentiment = overallSentiment,
                            isOverallSentimentLoading = false
                        )
                    }
                    .onFailure {
                        uiState = uiState.copy(
                            overallNewsSentiment = "긍정",
                            isOverallSentimentLoading = false,
                            error = it.localizedMessage
                        )
                    }
            } else {
                 uiState = uiState.copy(
                    overallNewsSentiment = "뉴스 없음",
                    isOverallSentimentLoading = false
                )
            }
        }
    }
}