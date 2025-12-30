package com.antigravity.petunjukarah.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Modern Premium Palette
val PrimaryIndigo = Color(0xFF6366F1)
val SecondaryViolet = Color(0xFFA855F7)
val AccentCyan = Color(0xFF06B6D4)
val SurfaceLight = Color(0xFFF8FAF6)
val TextMain = Color(0xFF0F172A)
val TextMuted = Color(0xFF64748B)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryIndigo,
    secondary = SecondaryViolet,
    tertiary = AccentCyan,
    background = Color(0xFF020617),
    surface = Color(0xFF0F172A),
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryIndigo,
    secondary = SecondaryViolet,
    tertiary = AccentCyan,
    background = Color(0xFFF8FAFC),
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = TextMain,
    onSurface = TextMain
)

@Composable
fun PetunjukArahTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
