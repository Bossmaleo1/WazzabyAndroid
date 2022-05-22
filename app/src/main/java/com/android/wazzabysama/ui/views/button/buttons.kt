package com.android.wazzabysama.ui.views.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun FloatingButton() {
    ExtendedFloatingActionButton(
        icon = { Icon(Icons.Filled.Favorite, "") },
        text = { Text("FloatingActionButton") },
        onClick = { /*do something*/ },
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    )
}