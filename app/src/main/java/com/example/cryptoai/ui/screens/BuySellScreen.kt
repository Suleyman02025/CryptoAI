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
fun BuySellScreen(type: String, symbol: String, onBack: () -> Unit, onContinue: (Double) -> Unit = {}) {
    val scrollState = rememberScrollState()
    val viewModel: PortfolioViewModel = viewModel()
    val coinData = remember { getTrendingCoins().find { it.symbol.equals(symbol, ignoreCase = true) } }
    var usdInput by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    val isBuy = type.equals("Buy", ignoreCase = true)
    val price = coinData?.price ?: 1.0
    val receiveAmount = usdInput.toDoubleOrNull()?.div(price) ?: 0.0
    val coinName = coinData?.name ?: symbol

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
                    text = "$type $symbol",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(24.dp))
                    .padding(20.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("You Pay", color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                            androidx.compose.material3.OutlinedTextField(
                                value = usdInput,
                                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) usdInput = it },
                                placeholder = { Text("USD Amount", color = Color(0xFFB0B0B0)) },
                                singleLine = true,
                                modifier = Modifier.width(120.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    color = Color.White,
                                    fontFamily = Urbanist,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp
                                )
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = R.drawable.ic_dollar), contentDescription = "USD", tint = Color.White, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("USD", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Icon(painter = painterResource(id = R.drawable.ic_arrow_down), contentDescription = "Dropdown", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Icon(painter = painterResource(id = R.drawable.ic_exchange), contentDescription = "Exchange", tint = Color(0xFFB0B0B0), modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("You Receive", color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                            Text("${"%.4f".format(receiveAmount)}", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(id = coinData?.iconRes ?: R.drawable.bitcoin), contentDescription = symbol, tint = Color.Unspecified, modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(symbol, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Icon(painter = painterResource(id = R.drawable.ic_arrow_down), contentDescription = "Dropdown", tint = Color.White, modifier = Modifier.size(18.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(8.dp).background(PrimaryGreen, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("1 $symbol = $${"%.5f".format(price)} USD", color = Color(0xFFB0B0B0), fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 13.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_dollar), contentDescription = "Fee", tint = Color.White, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Exchange Fee", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                        Text("0.09%", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("$${usdInput.ifBlank { "0.00" }}", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    text = "Click here for ",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                )
                Text(
                    text = "Terms & Conditions",
                    color = PrimaryGreen,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        val usd = usdInput.toDoubleOrNull() ?: 0.0
                        if (usd <= 0.0) return@Button
                        onContinue(usd)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isBuy) "Buy" else "Sell",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
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
    if (showSuccessDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text("Success") },
            text = { Text("Transaction completed!") },
            confirmButton = {
                Button(onClick = { showSuccessDialog = false; onContinue(0.0) }) {
                    Text("OK")
                }
            }
        )
    }
} 