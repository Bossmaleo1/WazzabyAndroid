package com.android.wazzabysama.ui.views.camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModel
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun VideoPreviewScreen(
    uri: String,
    navController: NavController,
    cameraViewModel: CameraViewModel
) {
    val screenState = cameraViewModel.screenState.value
    val context = LocalContext.current

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
        }
    }

    DisposableEffect(
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            AndroidView(
                factory = { context ->
                    StyledPlayerView(context).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            Box(
                Modifier.align(Alignment.TopStart)
            ) {
                IconButton(
                    modifier = Modifier.padding(bottom = 32.dp),
                    onClick = {
                        screenState.videoFile = null
                        screenState.videoCaptureUriEncoded = null
                        navController.navigate("video")
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "close",
                            tint = Color.White
                        )
                    }
                )
            }

            Box(
                Modifier.align(Alignment.TopEnd)
            ) {
                IconButton(
                    modifier = Modifier.padding(bottom = 32.dp),
                    onClick = {
                        navController.navigate(WazzabyNavigation.PUBLIC_NEW_MESSAGE)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = "Validate",
                            tint = Color.White
                        )
                    }
                )
            }
        }
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}