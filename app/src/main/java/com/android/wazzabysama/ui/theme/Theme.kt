package com.android.wazzabysama.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = Purple700,
    primaryVariant = Purple900,
    onPrimary = Color.White,
    secondary = Purple700,
    secondaryVariant = Purple900,
    onSecondary = Color.White,
    error = Purple800,
    onBackground = Color.Black,
)


private val DarkThemeColors = darkColors(
    primary = Purple300,
    primaryVariant = Purple700,
    onPrimary = Color.Black,
    secondary = Purple300,
    onSecondary = Color.Black,
    error = Purple200,
    onBackground = Color.White
)



@Composable
fun WazzabySamaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = WazzabySamaTypography,
        shapes = WazzabySamaShapes,
        content = content
    )
}