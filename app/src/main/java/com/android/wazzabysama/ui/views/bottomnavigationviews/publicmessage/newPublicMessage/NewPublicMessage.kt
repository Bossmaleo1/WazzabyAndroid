package com.android.wazzabysama.ui.views.bottomnavigationviews.publicmessage.newPublicMessage


import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.extensions.ExtensionMode
import androidx.camera.extensions.ExtensionsManager
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.util.LocationLiveData
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.android.wazzabysama.ui.views.handleLocationData
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

lateinit var outputDirectory: File
lateinit var cameraExecutor: ExecutorService

var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
var displayTopBar: MutableState<Boolean> = mutableStateOf(true)
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

    val cameraPermissionListState = rememberMultiplePermissionsState(
        mutableListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    )

    var contentText by rememberSaveable { mutableStateOf("") }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            if (displayTopBar.value) {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    },
                    actions = {

                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                navController.navigate("video")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.EmergencyRecording,
                                contentDescription = "Localized description"
                            )
                        }


                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                if (cameraPermissionListState.allPermissionsGranted) {
                                    navController.navigate(WazzabyNavigation.CAMERA)
                                } else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        cameraPermissionListState.launchMultiplePermissionRequest()
                                    }
                                }
                                //navController.navigate("video")
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AddAPhoto,
                                contentDescription = "Localized description"
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
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior,
                    title = {
                        Text(
                            text = "New Message",
                            fontWeight = FontWeight.Normal
                        )
                    }
                )

            }
        },
        content = { innerPadding ->


                OutlinedTextField(
                    value = contentText,
                    singleLine = false,
                    textStyle = TextStyle(fontSize = 12.sp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ),
                    onValueChange = {
                        contentText = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 0.dp, bottom = 0.dp, start = 5.dp, end = 5.dp),
                    shape = RoundedCornerShape(15.dp)
                )
        })

        //permission handler
        if (cameraPermissionListState.shouldShowRationale) {
            Log.d("MALEO9393", " important. Please grant all of them for the app to function properly.")
        } else {
            Log.d("MALEO9393", "denied. The app cannot function without them.")
        }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun RequestCameraPermission(navController: NavHostController) {
    val cameraPermissionListState = rememberMultiplePermissionsState(
        mutableListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    )

    if (cameraPermissionListState.allPermissionsGranted) {
        //
    } else {
        CoroutineScope(Dispatchers.Main).launch {
            cameraPermissionListState.launchMultiplePermissionRequest()
        }
    }

    if (cameraPermissionListState.shouldShowRationale) {
        Log.d("MALEO9393", " important. Please grant all of them for the app to function properly.")
    } else {
        Log.d("MALEO9393", "denied. The app cannot function without them.")
    }
}
