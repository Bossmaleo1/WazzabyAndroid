package com.android.wazzabysama.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

@Composable
fun getCameraButtomTint(clickedButton: MutableState<Boolean>, isDark: Boolean) : Color {
    if (!clickedButton.value) {
        if (isDark) {
           return  Color.White
        } else {
            return Color.Black
        }
    }
    return MaterialTheme.colorScheme.primary
}