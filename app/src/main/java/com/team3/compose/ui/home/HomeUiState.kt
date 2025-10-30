package com.team3.compose.ui.home

import com.team3.compose.domain.model.Stock

data class HomeUiState(
    val stocks: List<Stock> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)