package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.remember
import com.example.cryptoai.ui.screens.getTrendingCoins

@Composable
fun CoinDetailScreen(coin: String, onBack: () -> Unit, onBuyClick: () -> Unit = {}, onSellClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val trendingCoins = remember { getTrendingCoins() }
    val coinData = trendingCoins.find { it.symbol.equals(coin, ignoreCase = true) }
    val iconRes = coinData?.iconRes ?: R.drawable.bitcoin
    val iconBg = coinData?.iconBg ?: Color(0xFFF7931A)
    val name = coinData?.name ?: "Bitcoin"
    val symbol = coinData?.symbol ?: "BTC"
    val price = coinData?.price ?: 0.0
    val changePercent = coinData?.changePercent ?: 0.0
    val changeColor = if (changePercent >= 0) com.example.cryptoai.ui.theme.PrimaryGreen else Color(0xFFFF4B4B)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF131316))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            // Top Bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBack() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "$symbol/USD",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Coin Info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = name,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "$name/$symbol",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "$${"%,.2f".format(price)}",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(changeColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = (if (changePercent >= 0) "+" else "") + "%.2f".format(changePercent) + "%",
                        color = changeColor,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Chart Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_filter),
                            contentDescription = "Filter",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_top_traded),
                            contentDescription = "Expand",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Dummy chart area
                    CandlestickChart(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    // Time Range Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TimeRangeButton("1D", selected = true)
                        TimeRangeButton("1D")
                        TimeRangeButton("1W")
                        TimeRangeButton("1Y")
                        TimeRangeButton("5Y")
                        TimeRangeButton("All")
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Description",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s...",
                color = Color(0xFFB0B0B0),
                fontFamily = Urbanist,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onSellClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4B4B)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Sell",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { onBuyClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Buy",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TimeRangeButton(label: String, selected: Boolean = false) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) Color.White else Color(0xFF23232A))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            color = if (selected) Color.Black else Color.White,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun CandlestickChart(modifier: Modifier = Modifier) {
    // Dummy candlestick data: List of (open, close, high, low, isGreen)
    val candles = listOf(
        Candle(1800f, 1850f, 1870f, 1780f, true),
        Candle(1850f, 1820f, 1860f, 1810f, false),
        Candle(1820f, 1840f, 1850f, 1800f, true),
        Candle(1840f, 1880f, 1890f, 1830f, true),
        Candle(1880f, 1860f, 1900f, 1850f, false),
        Candle(1860f, 1840f, 1870f, 1830f, false),
        Candle(1840f, 1850f, 1860f, 1820f, true),
        Candle(1850f, 1830f, 1860f, 1820f, false)
    )
    val yLabels = listOf(1800f, 1820f, 1840f, 1860f, 1880f)
    val minY = 1800f
    val maxY = 1900f

    val labelPadding = 48f // px reserved for y-axis labels

    androidx.compose.foundation.Canvas(modifier = modifier.background(Color.Transparent)) {
        val w = size.width - labelPadding
        val h = size.height
        val candleWidth = w / (candles.size * 2.5f)
        val candleSpacing = w / (candles.size + 1)
        val priceToY = { price: Float -> h - ((price - minY) / (maxY - minY) * h) }

        // Draw grid lines and y-axis labels
        yLabels.forEach { yValue ->
            val y = priceToY(yValue)
            drawLine(
                color = Color(0xFF23232A),
                start = androidx.compose.ui.geometry.Offset(0f, y),
                end = androidx.compose.ui.geometry.Offset(w, y),
                strokeWidth = 1f
            )
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "$${yValue.toInt()}",
                    w + 8f,
                    y + 10f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 28f
                        textAlign = android.graphics.Paint.Align.LEFT
                    }
                )
            }
        }

        // Draw candlesticks
        candles.forEachIndexed { i, candle ->
            val x = candleSpacing * (i + 1)
            val color = if (candle.isGreen) Color(0xFF40BF6A) else Color(0xFFFF4B4B)
            // Wick
            drawLine(
                color = color,
                start = androidx.compose.ui.geometry.Offset(x, priceToY(candle.high)),
                end = androidx.compose.ui.geometry.Offset(x, priceToY(candle.low)),
                strokeWidth = 2f
            )
            // Body
            val top = priceToY(maxOf(candle.open, candle.close))
            val bottom = priceToY(minOf(candle.open, candle.close))
            drawRect(
                color = color,
                topLeft = androidx.compose.ui.geometry.Offset(x - candleWidth / 2, top),
                size = androidx.compose.ui.geometry.Size(candleWidth, (bottom - top).coerceAtLeast(2f))
            )
        }
    }
}

data class Candle(val open: Float, val close: Float, val high: Float, val low: Float, val isGreen: Boolean) 