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
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.android.wazzabysama.ui.views.utils.camera.createVideoCaptureUseCase
import com.android.wazzabysama.ui.views.utils.camera.getCameraProvider
import com.android.wazzabysama.ui.views.utils.camera.startRecordingVideo


lateinit var cameraExecutor: ExecutorService

/*suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        val extensionsManagerFuture =
            ExtensionsManager.getInstanceAsync(applicationContext, cameraProvider.get())
        cameraProvider.addListener({
            // Obtain an instance of the extensions manager
            // The extensions manager enables a camera to use extension capabilities available on
            // the device.
            val extensionsManager = extensionsManagerFuture.get()

            // Select the camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            // Query if extension is available.
            // Not all devices will support extensions or might only support a subset of
            // extensions.
            if (extensionsManager.isExtensionAvailable(cameraSelector, ExtensionMode.NIGHT)) {
                try {
                    cameraProvider.get().unbindAll()

                    // Retrieve a night extension enabled camera selector
                    val nightCameraSelector =
                        extensionsManager.getExtensionEnabledCameraSelector(
                            cameraSelector,
                            ExtensionMode.NIGHT
                        )

                    // Bind image capture and preview use cases with the extension enabled camera
                    // selector.
                    val imageCapture = ImageCapture.Builder().build()
                    val preview = Preview.Builder().build()
                    // Connect the preview to receive the surface the camera outputs the frames
                    // to. This will allow displaying the camera frames in either a TextureView
                    // or SurfaceView. The SurfaceProvider can be obtained from the PreviewView.
                    //preview.setSurfaceProvider(surfaceProvider)

                    // Returns an instance of the camera bound to the lifecycle
                    // Use this camera object to control various operations with the camera
                    // Example: flash, zoom, focus metering etc.
                    val camera = cameraProvider.get().bindToLifecycle(
                        applicationContext as LifecycleOwner,
                        nightCameraSelector,
                        imageCapture,
                        preview
                    )
                } catch (e: Exception) {
                    Log.e("MALEO9393MALEO9393", "Use case binding failed", e)
                }
            }

            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}*/

@ExperimentalMaterial3Api
@Composable
fun CameraUI(
    navController: NavHostController,
    cameraViewModel: CameraViewModel
) {
    cameraExecutor = Executors.newSingleThreadExecutor()
    BindCameraUseCases(navController, cameraViewModel)
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
    var clickedPhotoButton = remember { mutableStateOf(value = true) }
    var clickedVideoButton = remember { mutableStateOf(value = false) }
    var displayPhotoView = remember { mutableStateOf(value = true) }

    var recording: Recording? = remember { null }
    val recordingStarted: MutableState<Boolean> = remember { mutableStateOf(false) }
    val audioEnabled: MutableState<Boolean> = remember { mutableStateOf(true) }


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

    val qualityVideoSelector = QualitySelector
        .from(
            Quality.UHD,
            FallbackStrategy.higherQualityOrLowerThan(Quality.SD)
        )

    val recorder = Recorder.Builder()
        .setQualitySelector(qualityVideoSelector)
        .build()

    val videoCapture = VideoCapture.withOutput(recorder)

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

    val isDark = isSystemInDarkTheme()

    Box(Modifier.fillMaxSize()) {
            AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
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
                        modifier = Modifier,
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
                        modifier = Modifier,
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
                            modifier = Modifier,
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

            /*Box(
                Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
            ) {
                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(5.dp)
                    ) {
                        AssistChip(
                            onClick = {
                                clickedPhotoButton.value =  false
                                clickedVideoButton.value = true
                                displayPhotoView.value = false
                            },
                            label = {
                                Text(
                                    text = "Video",
                                    color = getCameraButtomTint(
                                        clickedButton = clickedVideoButton, isDark = isDark
                                    )
                                )
                            },
                            border = if (clickedVideoButton.value) AssistChipDefaults.assistChipBorder(
                                disabledBorderColor = Color.Red,
                                borderWidth = 1.dp,
                                borderColor = MaterialTheme.colorScheme.primary
                            ) else AssistChipDefaults.assistChipBorder(),
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Videocam,
                                    contentDescription = "Localized description",
                                    Modifier.size(AssistChipDefaults.IconSize),
                                    tint = getCameraButtomTint(
                                        clickedButton = clickedVideoButton,
                                        isDark = isDark
                                    )
                                )
                            }
                        )

                        Spacer(modifier = Modifier.size(10.dp))

                        AssistChip(
                            onClick = {
                                clickedPhotoButton.value = true
                                clickedVideoButton.value = false
                                displayPhotoView.value = true
                            },
                            label = {
                                Text(
                                    text = "Photo",
                                    color = getCameraButtomTint(
                                        clickedButton = clickedPhotoButton,
                                        isDark = isDark
                                    )
                                )
                            },
                            border = if (clickedPhotoButton.value) AssistChipDefaults.assistChipBorder(
                                disabledBorderColor = Color.Red,
                                borderWidth = 1.dp,
                                borderColor = MaterialTheme.colorScheme.primary
                            ) else AssistChipDefaults.assistChipBorder(),
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.PhotoCamera,
                                    contentDescription = "Localized description",
                                    Modifier.size(AssistChipDefaults.IconSize),
                                    tint = getCameraButtomTint(
                                        clickedButton = clickedPhotoButton,
                                        isDark = isDark
                                    )
                                )
                            }
                        )
                    }
                }
            }*/
        }

    }

}

