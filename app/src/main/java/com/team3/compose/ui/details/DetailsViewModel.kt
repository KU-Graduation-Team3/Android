package com.team3.compose.ui.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.core.model.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.model.FloatEntry
import com.patrykandpatrick.vico.core.model.entryModelOf
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

    val chartEntryModelProducer = ChartEntryModelProducer()

    init {
        loadStockDetails()
        prepareChartData()
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

    private fun prepareChartData() {
        val priceEntries = uiState.stockPriceHistory.mapIndexed { index, data ->
            FloatEntry(index.toFloat(), data.closePrice.toFloat())
        }
        val positiveEntries = uiState.stockPriceHistory.mapIndexed { index, data ->
            FloatEntry(index.toFloat(), data.positiveRatio.toFloat())
        }
        val negativeEntries = uiState.stockPriceHistory.mapIndexed { index, data ->
            FloatEntry(index.toFloat(), data.negativeRatio.toFloat())
        }
        chartEntryModelProducer.setModels(
            entryModelOf(priceEntries),
            entryModelOf(positiveEntries, negativeEntries)
        )
    }
}
