package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist
import com.example.cryptoai.ui.screens.CardData

@Composable
fun PaymentMethodScreen(onBack: () -> Unit, onContinue: () -> Unit = {}) {
    // Card data (same as MyCardsScreen)
    val cards = listOf(
        CardData(
            name = "Julia James",
            number = "****  ****  ****  1234",
            exp = "12/25",
            cvv = "123",
            brand = "VISA"
        ),
        CardData(
            name = "Mark Salt",
            number = "****  ****  ****  5678",
            exp = "11/26",
            cvv = "456",
            brand = "MASTERCARD"
        ),
        CardData(
            name = "Mark Salt",
            number = "****  ****  ****  5678",
            exp = "11/26",
            cvv = "456",
            brand = "PAYPAL"
        )
    )
    var selectedIndex by remember { mutableStateOf(0) }
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
                    text = "Payment Method",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Choose the payment method",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Card List
            cards.forEachIndexed { i, card ->
                PaymentCardItem(
                    card = card,
                    selected = selectedIndex == i,
                    onClick = { selectedIndex = i },
                    compact = true
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            // Continue Button
            Button(
                onClick = { onContinue() },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Text(
                    text = "Continue",
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
fun PaymentCardItem(card: CardData, selected: Boolean, onClick: () -> Unit, compact: Boolean = false) {
    val borderColor = if (selected) PrimaryGreen else Color.Transparent
    val gradient = when (card.brand) {
        "VISA" -> Brush.linearGradient(listOf(Color(0xFF1DB954), Color(0xFF159B4C)))
        "MASTERCARD" -> Brush.linearGradient(listOf(Color(0xFFFC5C7D), Color(0xFF6A82FB)))
        "PAYPAL" -> Brush.linearGradient(listOf(Color(0xFF003087), Color(0xFF009CDE)))
        else -> Brush.linearGradient(listOf(Color(0xFF23232A), Color(0xFF23232A)))
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(12.dp))
            .padding(if (compact) 12.dp else 20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Card Name", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = if (compact) 12.sp else 14.sp)
                    Text(card.name, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = if (compact) 14.sp else 18.sp)
                }
                Text(card.brand, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = if (compact) 16.sp else 22.sp)
            }
            Spacer(modifier = Modifier.height(if (compact) 8.dp else 16.dp))
            Text(card.number, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = if (compact) 14.sp else 20.sp)
            Spacer(modifier = Modifier.height(if (compact) 8.dp else 16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Exp Date", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = if (compact) 12.sp else 14.sp)
                    Text(card.exp, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = if (compact) 12.sp else 16.sp)
                }
                Column {
                    Text("CVV Number", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = if (compact) 12.sp else 14.sp)
                    Text(card.cvv, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = if (compact) 12.sp else 16.sp)
                }
            }
        }
    }
} 