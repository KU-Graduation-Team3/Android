package com.team3.compose.domain.model

data class NewsArticle(
    val id: String,
    val stockCode: String,
    val title: String,
    val publisher: String,
    val publishedAt: String,
    val sentiment: String, // 'positive', 'neutral', 'negative'
    val sentimentScore: Number
)

data class DetailedNews(
    val id: String,
    val stockCode: String,
    val title: String,
    val publisher: String,
    val publishedAt: String,
    val sentiment: String,
    val sentimentScore: Number,
    val sentences: List<SentenceSentiment>
)

data class SentenceSentiment(
    val text: String,
    val sentiment: Int // 1, 0, -1
)