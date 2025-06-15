package com.example.cryptoai.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoai.R
import com.example.cryptoai.ui.theme.PrimaryGreen
import com.example.cryptoai.ui.theme.Urbanist

@Composable
fun LoginScreen(onLogin: () -> Unit) {
    var email by remember { mutableStateOf("Suleyman@gmail.com") }
    var password by remember { mutableStateOf("2025") }
    var passwordVisible by remember { mutableStateOf(false) }
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
                .padding(horizontal = 24.dp)
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Hi Welcome\nBack !",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Email Address",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF18181C),
                    focusedContainerColor = Color(0xFF18181C),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryGreen,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Password",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF18181C), RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFF18181C),
                    focusedContainerColor = Color(0xFF18181C),
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = PrimaryGreen,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(id = if (passwordVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot Password",
                    color = PrimaryGreen,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onLogin,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Or Continue with",
                color = Color.White,
                fontFamily = Urbanist,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF18181C)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = "Google",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Google",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF18181C)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_apple),
                        contentDescription = "Apple",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Apple",
                        color = Color.White,
                        fontFamily = Urbanist,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.White,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                Text(
                    text = "Register",
                    color = PrimaryGreen,
                    fontFamily = Urbanist,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { }
                )
            }
        }
    }
} 