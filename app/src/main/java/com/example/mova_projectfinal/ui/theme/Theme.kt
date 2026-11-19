package com.example.mova_projectfinal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val MovaDarkColorScheme = darkColorScheme(
    primary = MovaRed,
    background = MovaBackground,
    surface = MovaSurface,
    onPrimary = Color.White,
    onBackground = MovaOnSurface,
    onSurface = MovaOnSurface,
    outline = MovaDivider
)


@Composable
fun Mova_ProjectFinalTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MovaDarkColorScheme,
        typography = Typography,
        content = content
    )
}