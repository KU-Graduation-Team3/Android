package com.team3.compose.ui.newsdetail

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
class NewsDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val newsId: String = savedStateHandle[Argument.NEWS_ID] ?: "news1"
    var uiState by mutableStateOf(NewsDetailUiState())
        private set

    init {
        loadNewsDetails()
    }

    private fun loadNewsDetails() {
        uiState = uiState.copy(isLoading = true)

        // For now, we only have mock data for "news1"
        if (newsId == MockData.detailedNews.id) {
            uiState = uiState.copy(
                detailedNews = MockData.detailedNews,
                isLoading = false
            )
        } else {
            // In a real app, you would fetch by id.
            // Here we show an error state if the ID is not the one we have.
            uiState = uiState.copy(
                isLoading = false,
                error = "News article not found."
            )
        }
    }
}