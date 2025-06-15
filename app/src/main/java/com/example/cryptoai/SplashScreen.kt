package com.example.cryptoai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF131316)),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "CRYPTO",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Ai",
                color = PrimaryGreen,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 44.sp,
                letterSpacing = 1.sp
            )
        }
    }
} 