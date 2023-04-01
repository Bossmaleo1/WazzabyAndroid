package com.android.wazzabysama.ui.views.bottomnavigationviews.publicmessage.newPublicMessage

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import androidx.compose.foundation.Image

lateinit var outputDirectory: File
lateinit var cameraExecutor: ExecutorService

var shouldShowCamera: MutableState<Boolean> = mutableStateOf(true)
var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
lateinit var photoUri: Uri


@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun NewPublicMessage(navController: NavHostController) {
    val isDark = isSystemInDarkTheme()
    val scaffoldState = rememberScaffoldState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    val listState = rememberLazyListState()
    // Camera permission state
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    outputDirectory = getOutputDirectory()
    cameraExecutor = Executors.newSingleThreadExecutor()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "",
                            /*tint = if (!isDark) {
                                colorResource(R.color.black40)
                            } else {
                                Color.White
                            }*/
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            //navController.navigate(Route.paymentView)
                            cameraPermissionState.launchPermissionRequest()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AddAPhoto,
                            contentDescription = "Localized description",
                            /*tint = if (!isDark) {
                                colorResource(R.color.black40)
                            } else {
                                Color.White
                            }*/
                        )
                    }


                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            //navController.navigate(Route.paymentView)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Done,
                            contentDescription = "Localized description",
                            /*tint = if (!isDark) {
                                colorResource(R.color.black40)
                            } else {
                                Color.White
                            }*/
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = "New Message",//stringResource(R.string.notifications),
                        /*color = if (!isDark) {
                            colorResource(R.color.black40)
                        } else {
                            Color.White
                        },*/
                        fontWeight = FontWeight.Normal
                    )
                }
            )
        },
        content = { innerPadding ->
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    isRefreshing = true
                    /*announcementViewModel.screenState.value.currentPage = 1
                    announcementViewModel.initAnnouncement()*/
                },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        // Pass the SwipeRefreshState + trigger through
                        state = state,
                        refreshTriggerDistance = trigger,
                        // Enable the scale animation
                        scale = true,
                        // Change the color and shape
                        backgroundColor = MaterialTheme.colors.primary.copy(
                            alpha = 0.08f
                        ),
                        shape = androidx.compose.material3.MaterialTheme.shapes.small,
                    )
                }
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 0.dp,
                        bottom = innerPadding.calculateBottomPadding() + 100.dp
                    ),
                    state = listState
                ) {

                }
            }

            if (cameraPermissionState.status.isGranted) {

                if (shouldShowCamera.value) {
                    CameraView(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor,
                        onImageCaptured = ::handleImageCapture,
                        onError = { Log.e("kilo", "View error:", it) }
                    )
                }

                if (shouldShowPhoto.value) {
                    Image(
                        painter = rememberImagePainter(photoUri),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        })

}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

private fun takePhoto(
    filenameFormat: String,
    imageCapture: ImageCapture,
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {

    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
        override fun onError(exception: ImageCaptureException) {
            Log.e("kilo", "Take photo error:", exception)
            onError(exception)
        }

        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
            val savedUri = Uri.fromFile(photoFile)
            onImageCaptured(savedUri)
        }
    })
}


fun handleImageCapture(uri: Uri) {
    Log.i("kilo", "Image captured: $uri")
    shouldShowCamera.value = false
    photoUri = uri
    shouldShowPhoto.value = true
}

@Composable
fun getOutputDirectory(): File {
    val context = LocalContext.current
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        File(it, "Hello World").apply { mkdirs() }
    }

    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

@Composable
fun CameraView(
    outputDirectory: File,
    executor: Executor,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    // 1
    val lensFacing = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val preview = Preview.Builder().build()
    val previewView = remember { PreviewView(context) }
    val imageCapture: ImageCapture = remember { ImageCapture.Builder().build() }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    // 2
    LaunchedEffect(lensFacing) {
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

    // 3
    Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())

        IconButton(
            modifier = Modifier.padding(bottom = 20.dp),
            onClick = {
                Log.i("kilo", "ON CLICK")
                takePhoto(
                    filenameFormat = "yyyy-MM-dd-HH-mm-ss-SSS",
                    imageCapture = imageCapture,
                    outputDirectory = outputDirectory,
                    executor = executor,
                    onImageCaptured = onImageCaptured,
                    onError = onError
                )
            },
            content = {
                Icon(
                    imageVector = Icons.Sharp.Lens,
                    contentDescription = "Take picture",
                    tint = Color.White,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(1.dp)
                        .border(1.dp, Color.White, CircleShape)
                )
            }
        )
    }
}
