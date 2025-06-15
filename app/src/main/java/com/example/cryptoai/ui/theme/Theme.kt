package com.example.cryptoai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    background = BackgroundWhite,
    surface = BackgroundWhite,
    onPrimary = BackgroundWhite,
    onBackground = Black,
    onSurface = Black,
    secondary = DarkGray,
    onSecondary = BackgroundWhite
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreen,
    background = Black,
    surface = DarkGray,
    onPrimary = Black,
    onBackground = BackgroundWhite,
    onSurface = BackgroundWhite,
    secondary = DarkGray,
    onSecondary = BackgroundWhite
)

@Composable
fun CryptoAiTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}