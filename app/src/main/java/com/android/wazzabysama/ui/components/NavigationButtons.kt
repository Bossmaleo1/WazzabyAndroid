package com.android.wazzabysama.ui.components

import androidx.compose.foundation.Image

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.android.wazzabysama.R

@Composable
@ExperimentalMaterial3Api
fun wazzabySamaIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.baseline_menu_18),
        contentDescription = null, // decorative
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
        modifier = modifier
    )
}

@Composable
@ExperimentalMaterial3Api
fun NavigationIcon(
    icon: Painter,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tintColor: Color? = null,
) {
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }

    val iconTintColor = tintColor ?: if (isSelected) {
        MaterialTheme.colorScheme.outline
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }
    Image(
        modifier = modifier,
        painter = icon,
        contentDescription = contentDescription,
        contentScale = ContentScale.Inside,
        colorFilter = ColorFilter.tint(iconTintColor),
        alpha = imageAlpha
    )
}