package com.team3.compose.domain.model

data class Stock(
    val code: String,
    val name: String,
    val currentPrice: Number,
    val changePercent: Number
)

data class StockPriceData(
    val date: String,
    val closePrice: Number,
    val positiveRatio: Number,
    val negativeRatio: Number
)