package com.android.wazzabysama.ui.views.camera

import android.content.Context
import android.util.Log
import android.util.Size
import androidx.camera.core.*
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.util.getCameraButtomTint
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModel
import com.android.wazzabysama.ui.UIEvent.Event.CameraEvent
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import android.Manifest
import android.net.Uri
import android.os.Build
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.android.wazzabysama.ui.views.utils.camera.createVideoCaptureUseCase
import com.android.wazzabysama.ui.views.utils.camera.getCameraProvider
import com.android.wazzabysama.ui.views.utils.camera.startRecordingVideo
import com.google.accompanist.permissions.rememberMultiplePermissionsState


lateinit var cameraExecutor: ExecutorService

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun CameraUI(
    navController: NavHostController,
    cameraViewModel: CameraViewModel
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    )
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }
    cameraExecutor = Executors.newSingleThreadExecutor()

    if(permissionState.allPermissionsGranted) {
        BindCameraUseCases(navController, cameraViewModel)
    } else {
        Column {
            val allPermissionsRevoked =
                permissionState.permissions.size ==
                        permissionState.revokedPermissions.size

            val textToShow = if (!allPermissionsRevoked) {
                // If not all the permissions are revoked, it's because the user accepted the COARSE
                // location permission, but not the FINE one.
                "Yay! Thanks for letting me access your approximate location. " +
                        "But you know what would be great? If you allow me to know where you " +
                        "exactly are. Thank you!"
            } else if (permissionState.shouldShowRationale) {
                // Both location permissions have been denied
                "Getting your exact location is important for this app. " +
                        "Please grant us fine location. Thank you :D"
            } else {
                // First time the user sees this feature or the user doesn't want to be asked again
                "This feature requires location permission"
            }

            val buttonText = if (!allPermissionsRevoked) {
                "Allow precise location"
            } else {
                "Request permissions"
            }

            Text(text = textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { permissionState.launchMultiplePermissionRequest() }) {
                Text(buttonText)
            }
        }
    }
    /*PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ }
    ) {
        BindCameraUseCases(navController, cameraViewModel)
    }*/
}

fun BindVideoUI() {
    val selector = QualitySelector
        .from(
            Quality.UHD,
            FallbackStrategy.higherQualityOrLowerThan(Quality.SD)
        )

    fun getResolutions(
        selector: CameraSelector,
        provider: ProcessCameraProvider
    ): Map<Quality, Size> {
        return selector.filter(provider.availableCameraInfos).firstOrNull()
            ?.let { camInfo ->
                QualitySelector.getSupportedQualities(camInfo)
                    .associateWith { quality ->
                        QualitySelector.getResolution(camInfo, quality)!!
                    }
            } ?: emptyMap()
    }
}

@ExperimentalMaterial3Api
@Composable
fun BindCameraUseCases(
    navController: NavHostController,
    cameraViewModel: CameraViewModel
) {
    val screenState = cameraViewModel.screenState.value
    var visibleRotateImage by remember { mutableStateOf(value = true) }

    val context = LocalContext.current
    //The preview use case
    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val lifecycleOwner = LocalLifecycleOwner.current
    // The image capture use case
    val imageCapture: ImageCapture = remember {
        ImageCapture
            .Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }
    //the camera select use case
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(screenState.lensFacing)
        .build()

    suspend fun cameraDisplayPreview() {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()

        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )



        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    LaunchedEffect(screenState.lensFacing) {
        cameraDisplayPreview()
    }

    LaunchedEffect(visibleRotateImage) {
        delay(3)
        visibleRotateImage = true
    }

    Box(Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Box(
            Modifier
                .align(Alignment.BottomCenter)
        ) {
            Box(
                Modifier.align(Alignment.TopCenter)
            ) {
                Box(
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(horizontal = 40.dp)
                ) {

                    IconButton(
                        modifier = Modifier.padding(bottom = 32.dp),
                        onClick = {},
                        content = {
                            Icon(
                                imageVector = Icons.Outlined.PhotoLibrary,
                                contentDescription = "Take picture",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(200.dp)
                            )
                        }
                    )

                }


                Box(
                    Modifier.align(Alignment.BottomCenter)
                ) {
                    IconButton(
                        modifier = Modifier.padding(bottom = 32.dp),
                        onClick = {
                            cameraViewModel.onEvent(
                                CameraEvent.CapturedButtonClicked(
                                    fileNameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                                    imageCapture = imageCapture,
                                    executor = cameraExecutor,
                                    navController = navController
                                )
                            )
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Outlined.Lens,
                                contentDescription = "Take picture",
                                tint = Color.White,
                                modifier = Modifier.size(200.dp)
                            )
                        }
                    )

                }

                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 40.dp)
                ) {

                    AnimatedVisibility(
                        visible = visibleRotateImage,
                        // By Default, `scaleIn` uses the center as its pivot point. When used with a vertical
                        // expansion from the vertical center, the content will be growing from the center of
                        // the vertically expanding layout.
                        enter = scaleIn() + expandVertically(expandFrom = Alignment.CenterVertically),
                        // By Default, `scaleOut` uses the center as its pivot point. When used with an
                        // ExitTransition that shrinks towards the center, the content will be shrinking both
                        // in terms of scale and layout size towards the center.
                        exit = scaleOut() + shrinkVertically(shrinkTowards = Alignment.CenterVertically)
                    ) {
                        IconButton(
                            modifier = Modifier.padding(bottom = 32.dp),
                            onClick = {
                                visibleRotateImage = false
                                cameraViewModel.onEvent(
                                    CameraEvent.ChangeCamera(screenState.lensFacing)
                                )
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Outlined.Cameraswitch,
                                    contentDescription = "Take picture",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(200.dp)
                                )
                            }
                        )
                    }

                }


                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(60.dp)
                ) {
                }
            }
        }

    }

}

