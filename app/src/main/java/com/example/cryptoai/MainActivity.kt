package com.example.cryptoai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cryptoai.navigation.NavigationScreen
import com.example.cryptoai.ui.theme.CryptoAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoAiTheme {
                NavigationScreen()
            }
        }
    }
}