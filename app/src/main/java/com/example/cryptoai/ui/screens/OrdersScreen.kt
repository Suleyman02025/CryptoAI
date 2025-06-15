package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cryptoai.ui.screens.getTrendingCoins

@Composable
fun OrdersScreen(onBack: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    val viewModel: PortfolioViewModel = viewModel()
    val orders = viewModel.orders
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
                    text = "My Orders",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            if (orders.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No orders yet.",
                        color = Color(0xFFB0B0B0),
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                }
            } else {
                Column(modifier = Modifier.fillMaxWidth()) {
                    orders.reversed().forEach { order ->
                        OrderItem(order)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OrderItem(order: PortfolioOrder) {
    val coinData = getTrendingCoins().find { it.symbol.equals(order.symbol, ignoreCase = true) }
    val coinIcon = coinData?.iconRes ?: R.drawable.bitcoin
    val coinName = coinData?.name ?: order.coin
    val coinSymbol = coinData?.symbol ?: order.symbol
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = coinIcon),
                        contentDescription = coinName,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(28.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(coinName, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("${"%.4f".format(order.quantity)} $coinSymbol", color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 13.sp)
                        Text(order.date, color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 12.sp)
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("$${"%,.2f".format(order.amount)}", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(order.type, color = if (order.type == "Buy") PrimaryGreen else Color(0xFFFF4B4B), fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Payment Method", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val paymentIcon = when (order.paymentMethod.uppercase()) {
                        "VISA" -> R.drawable.visa
                        "MASTERCARD" -> R.drawable.mastercard
                        "PAYPAL" -> R.drawable.paypal
                        else -> R.drawable.visa
                    }
                    Icon(
                        painter = painterResource(id = paymentIcon),
                        contentDescription = order.paymentMethod,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(order.paymentMethod.uppercase(), color = PrimaryGreen, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
} 