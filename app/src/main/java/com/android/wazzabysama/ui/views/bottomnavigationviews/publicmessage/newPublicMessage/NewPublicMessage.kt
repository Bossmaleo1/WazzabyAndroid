package com.android.wazzabysama.ui.views.bottomnavigationviews.publicmessage.newPublicMessage


import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModel
import com.android.wazzabysama.ui.UIEvent.Event.CameraEvent
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.common.base.Ascii.toLowerCase
import java.io.File
import java.util.*
import java.util.concurrent.ExecutorService

lateinit var outputDirectory: File
lateinit var cameraExecutor: ExecutorService

var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)
var displayTopBar: MutableState<Boolean> = mutableStateOf(true)
var shouldShowPhoto: MutableState<Boolean> = mutableStateOf(false)
lateinit var photoUri: Uri


@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun NewPublicMessage(
    navController: NavHostController,
    cameraViewModel: CameraViewModel
) {
    val isDark = isSystemInDarkTheme()
    val scaffoldState = rememberScaffoldState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var isDisplayPicture by remember { mutableStateOf(false) }
    val screenState = cameraViewModel.screenState.value
    //The imageUri for the Gallery
    var imageVideoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current

    //The bitmap for the Gallery
    val bitmap = remember {
        mutableStateOf<Bitmap?>(null)
    }
    //The launcher for the Gallery
    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageVideoUri = uri
        isDisplayPicture = true
        cameraViewModel.onEvent(CameraEvent.InitElement)
    }

    /*if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }*/

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE))

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            permissionState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(Unit) {
        if(screenState.photoFile !== null) {
            isDisplayPicture = true
        }
    }


    /*val cameraPermissionListState = rememberMultiplePermissionsState(
        mutableListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    )*/

    var contentText by rememberSaveable { mutableStateOf("") }

    /*PermissionsRequired(
        multiplePermissionsState = permissionState,
        permissionsNotGrantedContent = { /* ... */ },
        permissionsNotAvailableContent = { /* ... */ }
    ) {}*/

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
                                launcher.launch("*/*")
                                //navController.navigate(WazzabyNavigation.GALLERY)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PhotoLibrary,
                                contentDescription = "Localized description"
                            )
                        }

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
                                //if (cameraPermissionListState.allPermissionsGranted) {
                                    navController.navigate(WazzabyNavigation.CAMERA)
                                /*} else {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        cameraPermissionListState.launchMultiplePermissionRequest()
                                    }
                                }*/
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

            Column {

                AnimatedVisibility(
                    visible = isDisplayPicture,
                    enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)) { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        -fullWidth / 3
                    } + fadeIn(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 200)
                    ),
                    exit = slideOutHorizontally(animationSpec = spring(stiffness = Spring.StiffnessHigh)) {
                        // Overwrites the ending position of the slide-out to 200 (pixels) to the right
                        200
                    } + fadeOut()
                ) {




                    Box(modifier = Modifier
                        .height(60.dp)
                        .width(100.dp)) {
                        Box(Modifier.align(Alignment.Center)) {
                            imageVideoUri?.let {
                                val imageString = imageVideoUri.toString()
                                val imageVideoExtension = imageString.substring(imageString.lastIndexOf(".") + 1)
                                val videoExtensions = listOf("3gp","mp4","mkv","webm")
                                val imageExtensions = listOf("jpg","jpeg","png")

                                if (imageExtensions.contains(toLowerCase(imageVideoExtension))) {

                                    if (Build.VERSION.SDK_INT < 28) {
                                        bitmap.value = MediaStore.Images
                                            .Media.getBitmap(context.contentResolver, it)

                                    } else {
                                        val source = ImageDecoder
                                            .createSource(context.contentResolver, it)
                                        bitmap.value = ImageDecoder.decodeBitmap(source)
                                    }


                                    bitmap.value?.let { btm ->
                                        if (screenState.photoFile === null) {
                                            Image(
                                                bitmap = btm.asImageBitmap(),
                                                contentDescription = null,
                                                modifier = Modifier.size(50.dp))
                                        }
                                    }


                                } else if (videoExtensions.contains(toLowerCase(imageVideoExtension))) {
                                    Icon(
                                        imageVector = Icons.Outlined.Theaters,
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = null,
                                        modifier = Modifier.size(50.dp)
                                    )
                                } else {
                                    // SnackBar sur le fichier sélectionner
                                }
                            }

                            if (screenState.photoFile !== null) {
                                Image(
                                    painter = rememberImagePainter(Uri.fromFile(screenState.photoFile)),
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                        }


                        Box(modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(bottom = 40.dp, start = 50.dp)) {
                            IconButton(
                                modifier = Modifier,
                                onClick = {
                                    isDisplayPicture = false
                                    cameraViewModel.onEvent(CameraEvent.InitElement)
                                },
                                content = {
                                    Icon(
                                        imageVector = Icons.Outlined.Cancel,
                                        contentDescription = "Take picture",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                }
                            )
                        }


                    }
                }


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
            }
            /*Box() {

                Box(Modifier.align(Alignment.TopCenter)) {
                    Image(
                        painter = painterResource(id = R.drawable.photo),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp)
                        )
                }

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
            }*/
        })

        //permission handler
        /*if (cameraPermissionListState.shouldShowRationale) {
            Log.d("MALEO9393", " important. Please grant all of them for the app to function properly.")
        } else {
            Log.d("MALEO9393", "denied. The app cannot function without them.")
        }*/
}

/*@SuppressLint("CoroutineCreationDuringComposition")
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
}*/
