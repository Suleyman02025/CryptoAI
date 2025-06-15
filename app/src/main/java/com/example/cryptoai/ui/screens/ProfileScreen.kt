package com.example.cryptoai.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R

@Composable
fun ProfileScreen(onLogout: () -> Unit = {}, onMyCardsClick: () -> Unit = {}, onMyOrdersClick: () -> Unit = {}) {
    val scrollState = rememberScrollState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF131316))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(vertical = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Profile",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Profile picture
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Name and email
            Text(
                text = "Suleyman Kllasov",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "suleyman@gmail.com",
                color = Color(0xFFB0B0B0),
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "General",
                color = Color(0xFFB0B0B0),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(12.dp))
            ProfileOptionCard(icon = R.drawable.ic_profile, label = "My Account")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileOptionCard(icon = R.drawable.ic_order, label = "My Orders", onClick = onMyOrdersClick)
            Spacer(modifier = Modifier.height(12.dp))
            ProfileOptionCard(icon = R.drawable.ic_card, label = "My Cards", onClick = onMyCardsClick)
            Spacer(modifier = Modifier.height(12.dp))
            LogoutOptionCard(onClick = { showLogoutDialog = true })
        }
        if (showLogoutDialog) {
            LogoutDialog(
                onLogout = {
                    showLogoutDialog = false
                    onLogout()
                },
                onCancel = { showLogoutDialog = false }
            )
        }
    }
}

@Composable
fun ProfileOptionCard(icon: Int, label: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
            .padding(vertical = 18.dp, horizontal = 16.dp)
            .let { if (onClick != null) it.clickable { onClick() } else it },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0x1A40BF6A), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = Color(0xFF40BF6A),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LogoutOptionCard(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF18181C), RoundedCornerShape(16.dp))
            .padding(vertical = 18.dp, horizontal = 16.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0x19FF4B4B), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "Logout",
                tint = Color(0xFFFF4B4B),
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Logout",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LogoutDialog(onLogout: () -> Unit, onCancel: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x80000000), RoundedCornerShape(16.dp)),
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
                text = "Are You Sure You Want\nLogout?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 24.dp),
                lineHeight = 26.sp
            )
            Button(
                onClick = onLogout,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF40BF6A)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = "Logout",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Cancel",
                color = Color(0xFF40BF6A),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable { onCancel() }
                    .padding(8.dp)
            )
        }
    }
} 