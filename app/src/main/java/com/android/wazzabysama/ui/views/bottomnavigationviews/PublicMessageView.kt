package com.android.wazzabysama.ui.views.bottomnavigationviews

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PublicMessageView() {
    /*LazyColumn(contentPadding = innerPadding) {
        items(count = 2000) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
            ) {

                Text(text = "MALEO !!")
            }
        }
    }*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        for(i in 1..100) {
            Text(
                "User Name $i",
                style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(10.dp)
            )
            Divider(color = Color.Black, thickness = 5.dp)
        }
    }
}