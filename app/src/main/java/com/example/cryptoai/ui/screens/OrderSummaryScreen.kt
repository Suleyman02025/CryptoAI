package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

@Composable
fun OrderSummaryScreen(
    type: String,
    coinSymbol: String,
    amount: Double,
    paymentMethod: String,
    onBack: () -> Unit,
    onOrderConfirmed: () -> Unit = {}
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var orderConfirmed by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val coinData = getTrendingCoins().find { it.symbol.equals(coinSymbol, ignoreCase = true) }
    val coinIcon = coinData?.iconRes ?: R.drawable.bitcoin
    val coinName = coinData?.name ?: coinSymbol
    val coinSymbolDisplay = coinData?.symbol ?: coinSymbol
    val paymentIcon = when (paymentMethod.uppercase()) {
        "VISA" -> R.drawable.visa
        "MASTERCARD" -> R.drawable.mastercard
        "PAYPAL" -> R.drawable.paypal
        else -> R.drawable.visa
    }
    val viewModel: PortfolioViewModel = viewModel()
    val isBuy = type.equals("Buy", ignoreCase = true)
    val price = coinData?.price ?: 1.0
    val quantity = if (price > 0) amount / price else 0.0
    val feePercent = 0.09 / 100
    val fee = amount * feePercent
    val total = amount + fee

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
                    text = "Order Summary",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Balance",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "$9,876.09",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Order Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(20.dp))
                    .padding(20.dp)
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
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(coinName, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text(coinSymbolDisplay, color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("$${"%,.2f".format(amount)}", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("${"%.4f".format(quantity)} $coinSymbolDisplay", color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Payment Method
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Payment Method", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 15.sp)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = paymentIcon),
                                contentDescription = paymentMethod,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(paymentMethod.uppercase(), color = Color(0xFF1DB954), fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Purchase Price
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Purchase Price", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 15.sp)
                        Text("$${"%,.2f".format(price)}", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Fee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Fee (0.09%)", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 15.sp)
                        Text("$${"%,.2f".format(fee)}", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Divider
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFF23232A))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text("$${"%,.2f".format(total)}", color = PrimaryGreen, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            // Privacy
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_privacy),
                    contentDescription = "Privacy",
                    tint = PrimaryGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "Privacy Policy",
                    color = PrimaryGreen,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Confirm Order Button
            Button(
                onClick = { showConfirmDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Confirm Order",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
    // Confirm Dialog
    if (showConfirmDialog) {
        ConfirmOrderDialog(
            onConfirm = {
                showConfirmDialog = false
                if (isBuy) {
                    viewModel.buyToken(coinSymbol, amount, paymentMethod)
                    showSuccessDialog = true
                    orderConfirmed = true
                } else {
                    val success = viewModel.sellToken(coinSymbol, amount, paymentMethod)
                    if (success) {
                        showSuccessDialog = true
                        orderConfirmed = true
                    } else {
                        showErrorDialog = true
                    }
                }
            },
            onCancel = { showConfirmDialog = false }
        )
    }
    if (showErrorDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text("Error") },
            text = { Text("You don't have this asset or not enough to sell.") },
            confirmButton = {
                Button(onClick = { showErrorDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    // Success Dialog
    if (showSuccessDialog) {
        SuccessOrderDialog(onDismiss = {
            showSuccessDialog = false
            if (orderConfirmed) {
                onOrderConfirmed()
                orderConfirmed = false
            }
        })
    }
}

@Composable
fun ConfirmOrderDialog(onConfirm: () -> Unit, onCancel: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF18181C), RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .widthIn(min = 280.dp, max = 340.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Are You Sure You Want\nConfirm?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                lineHeight = 26.sp
            )
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Confirm",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cancel",
                color = PrimaryGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { onCancel() }
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun SuccessOrderDialog(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFF18181C), RoundedCornerShape(20.dp))
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .widthIn(min = 280.dp, max = 340.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_checked),
                contentDescription = "Success",
                tint = PrimaryGreen,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Your order completed",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp),
                lineHeight = 26.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "OK",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

data class OrderSummary(
    val coin: String,
    val amount: String,
    val quantity: String,
    val paymentMethod: String,
    val purchasePrice: String,
    val fee: String,
    val total: String
) 