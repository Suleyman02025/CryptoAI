package com.example.cryptoai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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

@Composable
fun IntroScreen(currentPage: Int, onContinue: () -> Unit) {
    val titles = listOf(
        "Exchange Your Crypto with Confidence",
        "Explore, Trade & Grow Your Crypto Portfolio"
    )
    val subtitles = listOf(
        "",
        ""
    )
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF131316))
    ) {
        // Skip button
        Text(
            text = "Skip",
            color = Color.White,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
        )

        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(id = R.drawable.intro),
                contentDescription = "Intro Image",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(0.5f)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .background(Color(0xFF18181C), shape = MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = titles[currentPage],
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = subtitles[currentPage],
                        color = Color(0xFFB0B0B0),
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Progress indicator and arrow button
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 32.dp, bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(2) { i ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            if (i == currentPage) PrimaryGreen else Color(0xFFB0B0B0),
                            shape = CircleShape
                        )
                )
                if (i == 0) Spacer(modifier = Modifier.width(8.dp))
            }
        }
        IconButton(
            onClick = onContinue,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 32.dp, bottom = 24.dp)
                .size(48.dp)
                .background(PrimaryGreen, shape = CircleShape)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = "Continue",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
} 