package com.team3.compose.ui.details

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.team3.compose.R
import com.team3.compose.domain.model.NewsArticle
import com.team3.compose.domain.model.SentimentScore
import com.team3.compose.domain.model.Stock
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = uiState.stock?.name ?: "종목 상세", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = uiState.stock?.code ?: "", color = Color.Gray, fontSize = 16.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9FAFB)
                )
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { CurrentPriceCard(stock = uiState.stock) }
            item { SentimentCard(sentiment = uiState.sentimentScore) }
            item { NewsListCard(news = uiState.newsArticles) }
        }
    }
}

@Composable
fun CurrentPriceCard(stock: Stock?) {
    if (stock == null) return
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            val change = stock.changePercent.toDouble()
            val color = if (change > 0) Color(0xFFDC2626) else Color(0xFF2563EB)
            val sign = if (change > 0) "+" else ""

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "${NumberFormat.getInstance().format(stock.currentPrice)}원",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "$sign$change%",
                    color = color,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun SentimentCard(sentiment: SentimentScore?) {
    if (sentiment == null) return
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("오늘의 뉴스 분석", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(16.dp))

            SentimentProgressBar(sentiment = sentiment)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SentimentLegendItem(color = Color(0xFF10B981), text = "긍정", value = sentiment.positive.toFloat())
                SentimentLegendItem(color = Color.Gray, text = "중립", value = sentiment.neutral.toFloat())
                SentimentLegendItem(color = Color(0xFFEF4444), text = "부정", value = sentiment.negative.toFloat())
            }
        }
    }
}

@Composable
fun SentimentProgressBar(sentiment: SentimentScore) {
    val positive = sentiment.positive.toFloat()
    val neutral = sentiment.neutral.toFloat()
    val negative = sentiment.negative.toFloat()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        Box(
            modifier = Modifier
                .weight(positive)
                .fillMaxHeight()
                .background(Color(0xFF10B981)), // Green
            contentAlignment = Alignment.Center
        ) {
            if (positive > 10) Text("${positive.toInt()}%", color = Color.White, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier
                .weight(neutral)
                .fillMaxHeight()
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
             // As per spec, don't show text if < 10%
        }
        Box(
            modifier = Modifier
                .weight(negative)
                .fillMaxHeight()
                .background(Color(0xFFEF4444)), // Red
            contentAlignment = Alignment.Center
        ) {
            if (negative > 10) Text("${negative.toInt()}%", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun SentimentLegendItem(color: Color, text: String, value: Float) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(color, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "$text: ${value.toInt()}%", fontSize = 14.sp)
    }
}

@Composable
fun NewsListCard(news: List<NewsArticle>) {
    Column {
         Text(
            text = "최신 관련 뉴스",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        news.forEach { article ->
            NewsItem(article = article)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun NewsItem(article: NewsArticle) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                   verticalAlignment = Alignment.CenterVertically
                ) {
                     val (iconRes, color) = when (article.sentiment) {
                        "positive" -> R.drawable.thumbs_up to Color(0xFF10B981)
                        "negative" -> R.drawable.thumbs_down to Color(0xFFEF4444)
                        else -> null to Color.Gray
                    }

                    if (iconRes != null) {
                         Icon(
                            painter = painterResource(id = iconRes), 
                            contentDescription = article.sentiment, 
                            tint = color,
                            modifier = Modifier.size(20.dp)
                         )
                    } else {
                        Text("—", color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = article.title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${article.publisher} • ${article.publishedAt}",
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun SentimentBadge(sentiment: String, score: Double) {
    val (bgColor, textColor, text) = when (sentiment) {
        "positive" -> Triple(Color(0xFFD1FAE5), Color(0xFF065F46), "긍정 ${String.format("%.2f", score)}")
        "negative" -> Triple(Color(0xFFFEE2E2), Color(0xFF991B1B), "부정 ${String.format("%.2f", score)}")
        else -> Triple(Color(0xFFF3F4F6), Color(0xFF374151), "중립 ${String.format("%.2f", score)}")
    }

    Box(
        modifier = Modifier
            .background(color = bgColor, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
