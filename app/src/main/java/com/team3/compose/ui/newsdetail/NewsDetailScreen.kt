package com.team3.compose.ui.newsdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.team3.compose.domain.model.DetailedNews
import com.team3.compose.domain.model.SentenceSentiment
import com.team3.compose.ui.details.SentimentBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<NewsDetailViewModel>()
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("뉴스 상세 분석", fontWeight = FontWeight.Bold, fontSize = 15.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기", modifier = Modifier.size(24.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9FAFB))
            )
        },
        containerColor = Color(0xFFF9FAFB)
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = 24.dp, start = 24.dp, end = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                uiState.detailedNews?.let {
                    ArticleInfoCard(news = it)
                }
            }
            item { SentimentLegendCard() }
            item {
                uiState.detailedNews?.let {
                    SentenceAnalysisCard(sentences = it.sentences)
                }
            }
        }
    }
}

@Composable
fun ArticleInfoCard(news: DetailedNews) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(news.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text("${news.publisher} • ${news.publishedAt}", color = Color.Gray, fontSize = 14.sp)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("기사 전체 감성:", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(8.dp))
                SentimentBadge(sentiment = news.sentiment, score = news.sentimentScore.toDouble())
            }
        }
    }
}

@Composable
fun SentimentLegendCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("문장별 감성 범례:", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(14.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
                , verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem(color = Color(0xFFD1FAE5), borderColor = Color(0xFF10B981), text = "긍정 (1)")
                LegendItem(color = Color(0xFFF3F4F6), borderColor = Color(0xFF9CA3AF), text = "중립 (0)")
                LegendItem(color = Color(0xFFFEE2E2), borderColor = Color(0xFFEF4444), text = "부정 (-1)")
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, borderColor: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .background(color)
                .border(1.dp, borderColor)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 14.sp)
    }
}

@Composable
fun SentenceAnalysisCard(sentences: List<SentenceSentiment>) {
    Column {
        Text(
            "문장별 감성 분석",
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        sentences.forEach { sentence ->
            SentenceItem(sentence = sentence)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun SentenceItem(sentence: SentenceSentiment) {
    val (bgColor, borderColor, label, labelColor) = when (sentence.sentiment.toInt()) {
        1 -> Quadruple(Color(0xFFD1FAE5), Color(0xFF10B981), "긍정", Color(0xFF065F46))
        -1 -> Quadruple(Color(0xFFFEE2E2), Color(0xFFEF4444), "부정", Color(0xFF991B1B))
        else -> Quadruple(Color(0xFFF3F4F6), Color(0xFF9CA3AF), "중립", Color(0xFF374151))
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            Text(
                text = "[$label]",
                color = labelColor,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = sentence.text,
                fontSize = 12.sp,
                lineHeight = 15.sp
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)