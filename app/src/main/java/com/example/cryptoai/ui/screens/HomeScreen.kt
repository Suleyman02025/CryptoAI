package com.example.cryptoai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist
import com.example.cryptoai.ui.screens.getMockPortfolioHoldings
import com.example.cryptoai.ui.screens.getTrendingCoins
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    viewModel: PortfolioViewModel,
    onPortfolioClick: () -> Unit = {},
    onSeeAllTrending: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val holdings = viewModel.portfolio
    val totalValue = holdings.sumOf { it.amount * it.price }
    val totalChange = holdings.sumOf { (it.amount * it.price) * (it.changePercent / 100.0) }
    val totalChangePercent = if (totalValue - totalChange != 0.0) (totalChange / (totalValue - totalChange)) * 100 else 0.0
    val gain = holdings.filter { it.changePercent > 0 }.sumOf { (it.amount * it.price) * (it.changePercent / 100.0) }
    val loss = holdings.filter { it.changePercent < 0 }.sumOf { (it.amount * it.price) * (it.changePercent / 100.0) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF131316))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            // Profile Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Suleyman Kllasov",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Online",
                        color = Color(0xFFB0B0B0),
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notification",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            // My Portfolio Card (Green)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryGreen, RoundedCornerShape(16.dp))
                    .padding(24.dp)
                    .clickable { onPortfolioClick() }
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "My Portfolio",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$${"%,.2f".format(totalValue)}",
                            color = Color.White,
                            fontFamily = Urbanist,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            modifier = Modifier.alignByBaseline()
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.alignByBaseline()
                        ) {
                            Icon(
                                painter = painterResource(id = if (totalChange >= 0) R.drawable.ic_arrow_up2 else R.drawable.ic_arrow_down2),
                                contentDescription = if (totalChange >= 0) "Up" else "Down",
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = (if (totalChange >= 0) "+" else "") + "%.2f".format(totalChangePercent) + "%",
                                color = Color.White,
                                fontFamily = Urbanist,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Gain/Loss Row (synced with PortfolioScreen)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                GainLossCard(
                    icon = R.drawable.ic_arrow_up,
                    iconTint = Color(0xFF40BF6A),
                    label = "Gain",
                    value = "$${"%,.2f".format(gain)}",
                    bgColor = Color(0xFF18181C),
                    modifier = Modifier.weight(1f)
                )
                GainLossCard(
                    icon = R.drawable.ic_arrow_down2,
                    iconTint = Color(0xFFFF4B4B),
                    label = "Loss",
                    value = "$${"%,.2f".format(-loss)}",
                    bgColor = Color(0xFF18181C),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Portfolio Section Title
            Text(
                text = "Portfolio",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Portfolio Cards Row (dynamic)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                holdings.take(2).forEach { holding ->
                    PortfolioCard(
                        modifier = Modifier.weight(1f),
                        iconRes = holding.iconRes,
                        name = holding.name,
                        symbol = holding.symbol,
                        price = "$${"%,.2f".format(holding.amount * holding.price)}",
                        change = (if (holding.changePercent >= 0) "+" else "") + "%.2f".format(holding.changePercent) + "%",
                        changeColor = if (holding.changePercent >= 0) Color(0xFF40BF6A) else Color(0xFFFF4B4B),
                        chartColor = if (holding.changePercent >= 0) Color(0xFF40BF6A) else Color(0xFFFF4B4B),
                        chartPoints = listOf(0.1f, 0.3f, 0.2f, 0.5f, 0.4f, 0.7f, 0.6f, 1f) // You can randomize or mock this
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton(icon = R.drawable.ic_send, label = "Send")
                ActionButton(icon = R.drawable.ic_receive, label = "Receive")
                ActionButton(icon = R.drawable.ic_exchange, label = "Exchange")
                ActionButton(icon = R.drawable.ic_more, label = "More")
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Trending Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Trending",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "See All",
                    color = Color(0xFFB0B0B0),
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onSeeAllTrending() }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            val trendingCoins = remember { getTrendingCoins() }
            trendingCoins.forEach { coin ->
                TrendingCard(
                    iconRes = coin.iconRes,
                    name = coin.name,
                    symbol = coin.symbol,
                    price = "$${"%,.2f".format(coin.price)}",
                    change = (if (coin.changePercent >= 0) "+" else "") + "%.2f".format(coin.changePercent) + "%",
                    changeColor = if (coin.changePercent >= 0) Color(0xFF40BF6A) else Color(0xFFFF4B4B),
                    iconBg = coin.iconBg
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ActionButton(icon: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF18181C))
                .clickable { }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.White,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
}

// PortfolioCard Composable
@Composable
fun PortfolioCard(
    modifier: Modifier = Modifier,
    iconRes: Int,
    name: String,
    symbol: String,
    price: String,
    change: String,
    changeColor: Color,
    chartColor: Color,
    chartPoints: List<Float>
) {
    Box(
        modifier = modifier
            .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icon in a circle, no background color
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = name,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = name,
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = symbol,
                        color = Color(0xFFB0B0B0),
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Chart
            PortfolioChart(
                points = chartPoints,
                color = chartColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Price and Change in a single line, fit to card
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = change,
                    color = changeColor,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    maxLines = 1,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

// PortfolioChart Composable
@Composable
fun PortfolioChart(points: List<Float>, color: Color, modifier: Modifier = Modifier) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        if (points.size < 2) return@Canvas
        val width = size.width
        val height = size.height
        val step = width / (points.size - 1)
        val minY = points.minOrNull() ?: 0f
        val maxY = points.maxOrNull() ?: 1f
        val rangeY = (maxY - minY).takeIf { it != 0f } ?: 1f
        val scaledPoints = points.mapIndexed { i, y ->
            val x = i * step
            val yScaled = height - ((y - minY) / rangeY) * height
            androidx.compose.ui.geometry.Offset(x, yScaled)
        }
        drawPath(
            path = androidx.compose.ui.graphics.Path().apply {
                moveTo(scaledPoints.first().x, scaledPoints.first().y)
                for (p in scaledPoints.drop(1)) lineTo(p.x, p.y)
            },
            color = color,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
        )
    }
}

// TrendingCard Composable
@Composable
fun TrendingCard(
    iconRes: Int,
    name: String,
    symbol: String,
    price: String,
    change: String,
    changeColor: Color,
    iconBg: Color = Color.Transparent
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = name,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = symbol,
                    color = Color(0xFFB0B0B0),
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = price,
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = change,
                    color = changeColor,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
} 