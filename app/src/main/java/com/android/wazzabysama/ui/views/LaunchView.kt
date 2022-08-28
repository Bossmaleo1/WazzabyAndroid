package com.android.wazzabysama.ui.views

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.android.wazzabysama.R
import kotlinx.coroutines.delay

@Composable
fun LaunchView() {
    var visibleImage by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            AnimatedVisibility(
                visibleImage,
                enter = slideIn(tween(100, easing = LinearOutSlowInEasing)) { fullSize ->
                    // Specifies the starting offset of the slide-in to be 1/4 of the width to the right,
                    // 100 (pixels) below the content position, which results in a simultaneous slide up
                    // and slide left.
                    IntOffset(fullSize.width / 4, 100)
                },
                exit = slideOut(tween(100, easing = FastOutSlowInEasing)) {
                    // The offset can be entirely independent of the size of the content. This specifies
                    // a target offset 180 pixels to the left of the content, and 50 pixels below. This will
                    // produce a slide-left combined with a slide-down.
                    IntOffset(-180, 50)
                },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wazzabysama),
                    contentDescription = "The Application Launcher"
                )
            }
        }
    }


    Column(
        modifier = Modifier.fillMaxSize()
                           .padding(60.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "©WazzabySama 2021 V 1.0",
            color = colorResource(R.color.Purple700))
    }

    LaunchedEffect(true) {
        delay(6)
        visibleImage = true
    }
}
