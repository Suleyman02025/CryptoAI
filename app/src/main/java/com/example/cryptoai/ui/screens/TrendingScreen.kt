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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.Urbanist

@Composable
fun TrendingScreen(onBack: () -> Unit) {
    val scrollState = rememberScrollState()
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
                    text = "Trending",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Trending",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            TrendingTokenCard(
                iconRes = R.drawable.bitcoin,
                name = "Bitcoin",
                symbol = "BTC",
                price = "$5,280.40",
                change = "+3.85%",
                changeColor = Color(0xFF40BF6A)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrendingTokenCard(
                iconRes = R.drawable.ethereum,
                name = "Ethereum",
                symbol = "ETH",
                price = "$4,200.65",
                change = "-5.67%",
                changeColor = Color(0xFFFF4B4B)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrendingTokenCard(
                iconRes = R.drawable.litecoin,
                name = "Litecoin",
                symbol = "LTC",
                price = "$3,200.65",
                change = "+6.67%",
                changeColor = Color(0xFF40BF6A)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrendingTokenCard(
                iconRes = R.drawable.tron,
                name = "Tron",
                symbol = "TRX",
                price = "$7,200.65",
                change = "-9.67%",
                changeColor = Color(0xFFFF4B4B)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrendingTokenCard(
                iconRes = R.drawable.xrp,
                name = "XRP",
                symbol = "XRP",
                price = "$2,100.72",
                change = "+5.67%",
                changeColor = Color(0xFF40BF6A)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrendingTokenCard(
                iconRes = R.drawable.dash,
                name = "Dash",
                symbol = "DAO",
                price = "$1,200.65",
                change = "-2.67%",
                changeColor = Color(0xFFFF4B4B)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TrendingTokenCard(
                iconRes = R.drawable.solana,
                name = "Solana",
                symbol = "SOL",
                price = "$8,200.65",
                change = "-6.67%",
                changeColor = Color(0xFFFF4B4B)
            )
        }
    }
}

@Composable
fun TrendingTokenCard(
    iconRes: Int,
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