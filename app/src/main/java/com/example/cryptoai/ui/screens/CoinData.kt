package com.example.cryptoai.ui.screens

import androidx.compose.ui.graphics.Color
import com.example.cryptoai.R

// Data class for trending/public coins
data class TrendingCoin(
    val iconRes: Int,
    val iconBg: Color,
    val name: String,
    val symbol: String,
    val price: Double,
    val changePercent: Double
)

fun getTrendingCoins(): List<TrendingCoin> = listOf(
    TrendingCoin(
        iconRes = R.drawable.bitcoin,
        iconBg = Color(0xFFF7931A),
        name = "Bitcoin",
        symbol = "BTC",
        price = 5280.40,
        changePercent = 3.85
    ),
    TrendingCoin(
        iconRes = R.drawable.ethereum,
        iconBg = Color(0xFFB1B1B1),
        name = "Ethereum",
        symbol = "ETH",
        price = 4200.65,
        changePercent = -5.67
    ),
    TrendingCoin(
        iconRes = R.drawable.xrp,
        iconBg = Color(0xFF4F4D4D),
        name = "XRP",
        symbol = "XRP",
        price = 1.12,
        changePercent = 2.15
    ),
    TrendingCoin(
        iconRes = R.drawable.litecoin,
        iconBg = Color(0xFF345D9D),
        name = "Litecoin",
        symbol = "LTC",
        price = 3200.65,
        changePercent = 6.67
    ),
    TrendingCoin(
        iconRes = R.drawable.tron,
        iconBg = Color(0xFFFF2D2D),
        name = "Tron",
        symbol = "TRX",
        price = 0.14,
        changePercent = -9.67
    )
) 