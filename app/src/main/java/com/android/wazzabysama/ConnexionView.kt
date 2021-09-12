package com.android.wazzabysama

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun Connexion() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.wazzabysama) ,
                contentDescription = "The Application Launcher",
                modifier = Modifier
                    .padding(4.dp)
                    .height(140.dp)
                    .width(100.dp)
            )
        }
    }
}