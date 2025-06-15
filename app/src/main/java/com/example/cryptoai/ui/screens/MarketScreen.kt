package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
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
import com.example.cryptoai.ui.theme.Urbanist
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.cryptoai.ui.screens.getTrendingCoins

@Composable
fun MarketScreen(onCoinClick: (String) -> Unit = {}) {
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
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Market",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Search Bar
            val searchText = remember { mutableStateOf("") }
            TextField(
                value = searchText.value,
                onValueChange = { searchText.value = it },
                placeholder = { Text("Search", color = Color(0xFFB0B0B0)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF18181C), RoundedCornerShape(24.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF18181C),
                    focusedContainerColor = Color(0xFF18181C),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Row of Cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MarketCategoryCard(
                    icon = R.drawable.ic_trending,
                    label = "Trending",
                    modifier = Modifier.weight(1f),
                    iconTint = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                MarketCategoryCard(
                    icon = R.drawable.ic_best_value,
                    label = "Best Value",
                    modifier = Modifier.weight(1f),
                    iconTint = Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                MarketCategoryCard(
                    icon = R.drawable.ic_top_traded,
                    label = "Top Traded",
                    modifier = Modifier.weight(1f),
                    iconTint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            val trendingCoins = remember { getTrendingCoins() }

            Text(
                text = "Top Search",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            trendingCoins.forEach { coin ->
                MarketTokenCard(
                    iconRes = coin.iconRes,
                    name = coin.name,
                    symbol = coin.symbol,
                    price = "$${"%,.2f".format(coin.price)}",
                    change = (if (coin.changePercent >= 0) "+" else "") + "%.2f".format(coin.changePercent) + "%",
                    changeColor = if (coin.changePercent >= 0) Color(0xFF40BF6A) else Color(0xFFFF4B4B),
                    onClick = { onCoinClick(coin.symbol) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

data class MarketToken(
    val iconRes: Int,
    val name: String,
    val symbol: String,
    val price: String,
    val change: String,
    val changeColor: Color
)

@Composable
fun MarketCategoryCard(icon: Int, label: String, modifier: Modifier = Modifier, iconTint: Color = Color.Black) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
            .padding(vertical = 24.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = iconTint,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.White,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun MarketTokenCard(
    iconRes: Int,
    name: String,
    symbol: String,
    price: String,
    change: String,
    changeColor: Color,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable { onClick() }
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
