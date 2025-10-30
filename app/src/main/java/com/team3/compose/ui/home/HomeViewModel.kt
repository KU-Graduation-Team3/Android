package com.team3.compose.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.team3.compose.data.MockData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        loadStocks()
    }

    private fun loadStocks() {
        uiState = uiState.copy(
            stocks = MockData.stockList,
            isLoading = false
        )
    }
}