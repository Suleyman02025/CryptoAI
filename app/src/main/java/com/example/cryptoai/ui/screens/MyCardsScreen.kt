package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist
import com.example.cryptoai.ui.screens.CardData

@Composable
fun MyCardsScreen(onBack: () -> Unit) {
    var selectedType by remember { mutableStateOf(1) }

    // Card data for each type
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
                    text = "Saved Cards" +
                            "" +
                            "",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Card Type",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card Type Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardTypeButton(
                    selected = selectedType == 0,
                    icon = R.drawable.mastercard,
                    label = "Mastercard",
                    onClick = { selectedType = 0 },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CardTypeButton(
                    selected = selectedType == 1,
                    icon = R.drawable.visa,
                    label = "VISA",
                    onClick = { selectedType = 1 },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CardTypeButton(
                    selected = selectedType == 2,
                    icon = R.drawable.paypal,
                    label = "PayPal",
                    onClick = { selectedType = 2 },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card Display
            when (selectedType) {
                0 -> CardDisplay(
                    card = cards[1],
                    gradient = Brush.linearGradient(
                        colors = listOf(Color(0xFFFC5C7D), Color(0xFF6A82FB)),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 400f)
                    )
                )
                1 -> CardDisplay(
                    card = cards[0],
                    gradient = Brush.linearGradient(
                        colors = listOf(Color(0xFF1DB954), Color(0xFF159B4C)),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 400f)
                    )
                )
                2 -> CardDisplay(
                    card = cards[2],
                    gradient = Brush.linearGradient(
                        colors = listOf(Color(0xFF003087), Color(0xFF009CDE)),
                        start = Offset(0f, 0f),
                        end = Offset(400f, 400f)
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Add Card Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Transparent)
                    .border(width = 1.dp, color = PrimaryGreen, shape = RoundedCornerShape(12.dp))
                    .clickable { /* TODO: Add card logic */ }
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+ Add Card",
                    color = PrimaryGreen,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CardTypeButton(
    selected: Boolean,
    icon: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) Color.Transparent else Color(0xFF23232A))
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) PrimaryGreen else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color.Unspecified, // No blue tint for PayPal
                modifier = Modifier.size(32.dp)
            )
            if (label == "PayPal") {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "",
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
fun CardDisplay(card: CardData, gradient: Brush) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient, shape = RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Card Name", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                    Text(card.name, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Text(card.brand, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(card.number, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Exp Date", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                    Text(card.exp, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Column {
                    Text("CVV Number", color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Normal, fontSize = 14.sp)
                    Text(card.cvv, color = Color.White, fontFamily = Urbanist, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
