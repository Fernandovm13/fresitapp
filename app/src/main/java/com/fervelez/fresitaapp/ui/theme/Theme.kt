package com.fervelez.fresitaapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme = darkColorScheme(
    primary = FresitaRed,
    secondary = FresitaGreen,
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = CardWhite,
    onSecondary = CardWhite,
    onBackground = CardWhite,
    onSurface = CardWhite
)

private val LightColorScheme = lightColorScheme(
    primary = FresitaRed,
    secondary = FresitaGreen,
    tertiary = FresitaOrange,

    background = FresitaOrange,
    surface = CardWhite,
    surfaceVariant = SurfaceSoft,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,

    onBackground = TextPrimary,
    onSurface = TextPrimary
)

@Composable
fun FresitaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
