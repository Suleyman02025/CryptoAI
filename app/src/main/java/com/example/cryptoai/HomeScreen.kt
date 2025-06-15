package com.example.cryptoai

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist
import com.example.cryptoai.R

@Composable
fun HomeScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF131316))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            // Profile Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Mark Salt",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Online",
                        color = Color(0xFFB0B0B0),
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = "Notification",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Portfolio Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryGreen, RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                Column {
                    Text(
                        text = "My Portfolio",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$34,010.00",
                            color = Color.White,
                            fontFamily = Urbanist,
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "+2.5%",
                                color = Color.White,
                                fontFamily = Urbanist,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton(icon = R.drawable.ic_send, label = "Send")
                ActionButton(icon = R.drawable.ic_receive, label = "Receive")
                ActionButton(icon = R.drawable.ic_exchange, label = "Exchange")
                ActionButton(icon = R.drawable.ic_more, label = "More")
            }
        }
    }
}

@Composable
fun ActionButton(icon: Int, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF18181C))
                .clickable { }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.White,
            fontFamily = Urbanist,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    }
} 