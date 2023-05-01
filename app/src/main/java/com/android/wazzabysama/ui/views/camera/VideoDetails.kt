package com.android.wazzabysama.ui.views.camera

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun VideoDetails(
    navController: NavHostController,
    cameraViewModel: CameraViewModel
) {

    val screenState = cameraViewModel.screenState.value
    val context = LocalContext.current
    val uriSomething = URLEncoder.encode(
        screenState.videoCaptureUriEncoded.toString(),
        StandardCharsets.UTF_8.toString()
    )

    Log.d("MALEOVIDEOENCODER9393","${screenState.videoCaptureUriEncoded}")
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uriSomething))
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
        }
    ) {
        onDispose {
            exoPlayer.release()
        }
    }
}