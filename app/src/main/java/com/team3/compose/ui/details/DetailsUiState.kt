package com.team3.compose.ui.details

import com.team3.compose.domain.model.NewsArticle
import com.team3.compose.domain.model.SentimentScore
import com.team3.compose.domain.model.Stock
import com.team3.compose.domain.model.StockPriceData

data class DetailsUiState(
    val stock: Stock? = null,
    val sentimentScore: SentimentScore? = null,
    val stockPriceHistory: List<StockPriceData> = emptyList(),
    val newsArticles: List<NewsArticle> = emptyList(),
    val overallNewsSentiment: String? = null,
    val isOverallSentimentLoading: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)