package com.team3.compose.ui.newsdetail

import com.team3.compose.domain.model.DetailedNews

data class NewsDetailUiState(
    val detailedNews: DetailedNews? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)