package com.android.wazzabysama.ui.view.bottomnavigationviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PublicMessageView() {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Green)) {
        Text("View 1")
    }
}