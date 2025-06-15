package com.example.cryptoai.ui.screens

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
import androidx.lifecycle.viewmodel.compose.viewModel

// Data classes for realistic portfolio
data class PortfolioHolding(
    val iconRes: Int,
    val iconBg: Color,
    val name: String,
    val symbol: String,
    val amount: Double,
    val price: Double,
    val changePercent: Double
)

fun getMockPortfolioHoldings(): List<PortfolioHolding> = listOf(
    PortfolioHolding(
        iconRes = R.drawable.bitcoin,
        iconBg = Color(0xFFF7931A),
        name = "Bitcoin",
        symbol = "BTC",
        amount = 0.75,
        price = 5280.40,
        changePercent = 3.85
    ),
    PortfolioHolding(
        iconRes = R.drawable.ethereum,
        iconBg = Color(0xFFB1B1B1),
        name = "Ethereum",
        symbol = "ETH",
        amount = 2.1,
        price = 4200.65,
        changePercent = -5.67
    ),
    PortfolioHolding(
        iconRes = R.drawable.litecoin,
        iconBg = Color(0xFF345D9D),
        name = "Litecoin",
        symbol = "LTC",
        amount = 10.0,
        price = 3200.65,
        changePercent = 6.67
    ),
    PortfolioHolding(
        iconRes = R.drawable.tron,
        iconBg = Color(0xFFFF2D2D),
        name = "Tron",
        symbol = "TRX",
        amount = 5000.0,
        price = 0.14,
        changePercent = -9.67
    )
)

@Composable
fun PortfolioScreen(
    viewModel: PortfolioViewModel,
    onBack: () -> Unit
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
                    text = "Portfolio",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Green Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryGreen, RoundedCornerShape(20.dp))
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Portfolio Value",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
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
            Spacer(modifier = Modifier.height(24.dp))
            // Gain/Loss Row
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
            Spacer(modifier = Modifier.height(24.dp))
            // Token List Title and Filter
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Token List",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Token Cards (dynamic)
            holdings.forEach { holding ->
                PortfolioTokenCard(
                    iconRes = holding.iconRes,
                    iconBg = holding.iconBg,
                    name = holding.name,
                    symbol = holding.symbol,
                    price = "$${"%,.2f".format(holding.amount * holding.price)}",
                    change = (if (holding.changePercent >= 0) "+" else "") + "%.2f".format(holding.changePercent) + "%",
                    changeColor = if (holding.changePercent >= 0) Color(0xFF40BF6A) else Color(0xFFFF4B4B)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun GainLossCard(icon: Int, iconTint: Color, label: String, value: String, bgColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(bgColor, RoundedCornerShape(16.dp))
            .padding(vertical = 18.dp, horizontal = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color(0xFF23232A), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = label,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = label,
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Text(
                    text = value,
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun PortfolioTokenCard(
    iconRes: Int,
    iconBg: Color,
    name: String,
    symbol: String,
    price: String,
    change: String,
    changeColor: Color
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