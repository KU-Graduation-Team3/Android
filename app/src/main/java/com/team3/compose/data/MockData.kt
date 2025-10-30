package com.team3.compose.data

import com.team3.compose.domain.model.*

object MockData {

    val stockList = listOf(
        Stock(code = "005930", name = "삼성전자", currentPrice = 71500, changePercent = 2.3),
        Stock(code = "000660", name = "SK하이닉스", currentPrice = 142000, changePercent = -1.5),
        Stock(code = "035420", name = "NAVER", currentPrice = 228500, changePercent = 3.7),
        Stock(code = "005380", name = "현대차", currentPrice = 195000, changePercent = 1.2),
        Stock(code = "051910", name = "LG화학", currentPrice = 385000, changePercent = -0.8),
        Stock(code = "006400", name = "삼성SDI", currentPrice = 428000, changePercent = 2.1),
        Stock(code = "035720", name = "카카오", currentPrice = 52300, changePercent = -2.3),
        Stock(code = "068270", name = "셀트리온", currentPrice = 178500, changePercent = 1.8),
        Stock(code = "207940", name = "삼성바이오로직스", currentPrice = 892000, changePercent = 0.5),
        Stock(code = "373220", name = "LG에너지솔루션", currentPrice = 412000, changePercent = -1.2)
    )

    val sentimentScores = mapOf(
        "005930" to SentimentScore(positive = 60, neutral = 10, negative = 30),
        "000660" to SentimentScore(positive = 45, neutral = 15, negative = 40),
        "035420" to SentimentScore(positive = 70, neutral = 20, negative = 10),
        "005380" to SentimentScore(positive = 55, neutral = 25, negative = 20)
    )

    val newsArticles = listOf(
        NewsArticle(id = "news1", stockCode = "005930", title = "삼성전자, 4분기 실적 시장 기대치 상회... \"역대 최고 수준\"", publisher = "한국경제", publishedAt = "2시간 전", sentiment = "positive", sentimentScore = 0.85),
        NewsArticle(id = "news2", stockCode = "005930", title = "삼성전자 파운드리 사업, 글로벌 점유율 확대 전망", publisher = "매일경제", publishedAt = "4시간 전", sentiment = "positive", sentimentScore = 0.72),
        NewsArticle(id = "news3", stockCode = "005930", title = "반도체 수급난 지속... 삼성전자 투자 심리 위축 우려", publisher = "서울경제", publishedAt = "5시간 전", sentiment = "negative", sentimentScore = -0.68),
        NewsArticle(id = "news4", stockCode = "005930", title = "삼성전자, AI 반도체 시장 공략 가속화", publisher = "이데일리", publishedAt = "7시간 전", sentiment = "positive", sentimentScore = 0.79),
        NewsArticle(id = "news5", stockCode = "005930", title = "삼성전자 임원 인사 단행, 조직 개편 발표", publisher = "조선일보", publishedAt = "8시간 전", sentiment = "neutral", sentimentScore = 0.05),
        NewsArticle(id = "news6", stockCode = "005930", title = "중국 반도체 기업과의 경쟁 심화... 가격 압박 불가피", publisher = "연합뉴스", publishedAt = "10시간 전", sentiment = "negative", sentimentScore = -0.55),
        NewsArticle(id = "news7", stockCode = "005930", title = "삼성전자, 차세대 메모리 반도체 개발 성공", publisher = "한국일보", publishedAt = "12시간 전", sentiment = "positive", sentimentScore = 0.88),
        NewsArticle(id = "news8", stockCode = "005930", title = "삼성전자 스마트폰 부문, 점유율 소폭 하락", publisher = "SBS Biz", publishedAt = "14시간 전", sentiment = "negative", sentimentScore = -0.42),
        NewsArticle(id = "news9", stockCode = "005930", title = "삼성전자, 친환경 경영 강화... ESG 평가 우수", publisher = "MBN", publishedAt = "1일 전", sentiment = "positive", sentimentScore = 0.64),
        NewsArticle(id = "news10", stockCode = "005930", title = "삼성전자 주가, 외국인 순매수에 상승세", publisher = "KBS", publishedAt = "1일 전", sentiment = "positive", sentimentScore = 0.71)
    )

    val stockPriceHistory = listOf(
        StockPriceData(date = "10/01", closePrice = 68500, positiveRatio = 45, negativeRatio = 35),
        StockPriceData(date = "10/02", closePrice = 68800, positiveRatio = 48, negativeRatio = 32),
        StockPriceData(date = "10/03", closePrice = 69200, positiveRatio = 52, negativeRatio = 28),
        StockPriceData(date = "10/04", closePrice = 69100, positiveRatio = 50, negativeRatio = 30),
        StockPriceData(date = "10/05", closePrice = 69500, positiveRatio = 55, negativeRatio = 25),
        StockPriceData(date = "10/08", closePrice = 70200, positiveRatio = 58, negativeRatio = 22),
        StockPriceData(date = "10/09", closePrice = 70100, positiveRatio = 56, negativeRatio = 24),
        StockPriceData(date = "10/10", closePrice = 69800, positiveRatio = 53, negativeRatio = 27),
        StockPriceData(date = "10/11", closePrice = 70500, positiveRatio = 59, negativeRatio = 21),
        StockPriceData(date = "10/12", closePrice = 70800, positiveRatio = 62, negativeRatio = 18),
        StockPriceData(date = "10/15", closePrice = 71200, positiveRatio = 65, negativeRatio = 15),
        StockPriceData(date = "10/16", closePrice = 71500, positiveRatio = 60, negativeRatio = 30)
    )

    val detailedNews = DetailedNews(
        id = "news1",
        stockCode = "005930",
        title = "삼성전자, 4분기 실적 시장 기대치 상회... \"역대 최고 수준\"",
        publisher = "한국경제",
        publishedAt = "2시간 전",
        sentiment = "positive",
        sentimentScore = 0.85,
        sentences = listOf(
            SentenceSentiment(text = "삼성전자가 4분기 실적이 시장 기대치를 크게 상회했다고 발표했습니다.", sentiment = 1),
            SentenceSentiment(text = "매출액은 76조 원, 영업이익은 10조 5천억 원을 기록하며 역대 최고 수준을 달성했습니다.", sentiment = 1),
            SentenceSentiment(text = "특히 메모리 반도체 부문의 실적 개선이 두드러졌습니다.", sentiment = 1),
            SentenceSentiment(text = "DS부문 매출은 전년 대비 20% 증가한 30조 원을 기록했습니다.", sentiment = 1),
            SentenceSentiment(text = "다만, 글로벌 반도체 수급난은 여전한 위험 요인으로 지적되고 있습니다.", sentiment = -1),
            SentenceSentiment(text = "회사 관계자는 \"향후 수요 증가에 대비해 생산 능력을 확대할 계획\"이라고 밝혔습니다.", sentiment = 1),
            SentenceSentiment(text = "시장에서 삼성전자의 5G 및 AI 반도체 경쟁력이 강화될 것으로 전망하고 있습니다.", sentiment = 1)
        )
    )
}
