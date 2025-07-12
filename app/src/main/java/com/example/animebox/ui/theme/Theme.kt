package com.example.animebox.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SoftPinkDark.copy(alpha = 0.7f),
    secondary = SoftPinkAccent.copy(alpha = 0.7f),
    tertiary = SoftPink.copy(alpha = 0.7f),
    background = Black,
    surface = Black,
    surfaceVariant = Black,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
    onBackground = White,
    onSurface = White,
    onSurfaceVariant = White,
    primaryContainer = Black,
    secondaryContainer = Black,
    tertiaryContainer = Black,
    onPrimaryContainer = White,
    onSecondaryContainer = White,
    onTertiaryContainer = White
)

private val LightColorScheme = lightColorScheme(
    primary = SoftPink,
    secondary = SoftPinkDark,
    tertiary = SoftPinkAccent,
    background = White,
    surface = White,
    surfaceVariant = White,
    onPrimary = Black,
    onSecondary = Black,
    onTertiary = Black,
    onBackground = Black,
    onSurface = Black,
    onSurfaceVariant = Black,
    primaryContainer = White,
    secondaryContainer = White,
    tertiaryContainer = White,
    onPrimaryContainer = Black,
    onSecondaryContainer = Black,
    onTertiaryContainer = Black
)

@Composable
fun AnimeBoxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            if (darkTheme) {
                window.statusBarColor = Black.toArgb()
                window.navigationBarColor = Black.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            } else {
                window.statusBarColor = White.toArgb()
                window.navigationBarColor = White.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}