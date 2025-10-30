package com.team3.compose.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.team3.compose.data.MockData
import com.team3.compose.ui.Argument
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val stockCode: String = savedStateHandle[Argument.STOCK_CODE] ?: "005930"
    var uiState by mutableStateOf(DetailsUiState())
        private set


    init {
        loadStockDetails()
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

}
