package com.android.wazzabysama.ui.views.camera

import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Autorenew
import androidx.compose.material.icons.outlined.Cameraswitch
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Crop
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModel
import com.android.wazzabysama.ui.UIEvent.Event.CameraEvent
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.android.wazzabysama.ui.views.bottomnavigationviews.getOurPublicMessageImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun ImageDetails(
    navController: NavHostController,
    cameraViewModel: CameraViewModel
) {
    val screenState = cameraViewModel.screenState.value

    Box(Modifier.fillMaxSize()) {
        Image(
            painter = rememberImagePainter(Uri.fromFile(screenState.photoFile)),
            contentDescription = "Profile picture description",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            Modifier.align(Alignment.TopStart)
        ) {
            IconButton(
                modifier = Modifier,
                onClick = {
                    screenState.imageCapturedUri = null
                    screenState.photoFile = null
                    navController.navigate(WazzabyNavigation.CAMERA)
                },
                content = {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Take picture",
                        tint = Color.White
                    )
                }
            )
        }

        Box(
            Modifier.align(Alignment.TopEnd)
        ) {

            Row {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                              cameraViewModel.onEvent(
                                  CameraEvent.Rotate
                              )
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Autorenew,
                            contentDescription = "Take picture",
                            tint = Color.White
                        )
                    }
                )

                IconButton(
                    modifier = Modifier,
                    onClick = {
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Crop,
                            contentDescription = "Take picture",
                            tint = Color.White
                        )
                    }
                )

                IconButton(
                    modifier = Modifier,
                    onClick = {
                        navController.navigate(WazzabyNavigation.PUBLIC_NEW_MESSAGE)
                    },
                    content = {
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = "Take picture",
                            tint = Color.White
                        )
                    }
                )
            }
        }
    }
}