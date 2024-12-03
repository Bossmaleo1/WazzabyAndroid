package com.android.wazzabysama.presentation.viewModel.camera

import android.app.Application
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.util.filterBitmapUpsideDown
import com.android.wazzabysama.presentation.util.generateFile
import com.android.wazzabysama.ui.UIEvent.Event.CameraEvent
import com.android.wazzabysama.ui.UIEvent.ScreenState.CameraScreenState.CameraScreenState
import com.android.wazzabysama.ui.UIEvent.UIEvent
import com.android.wazzabysama.ui.components.WazzabyNavigation
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val _screenState = mutableStateOf(
        CameraScreenState(
            imageCapturedUri = null
        )
    )

    val screenState: State<CameraScreenState> = _screenState
    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()


    private fun takePhoto(
        filenameFormat: String,
        imageCapture: ImageCapture,
        outputDirectory: File,
        executor: Executor,
        onImageCaptured: (File) -> Unit,
        onError: (ImageCaptureException) -> Unit,
        navController: NavController
    ) {

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(filenameFormat, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions, executor, object: ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageCaptured(photoFile)
                viewModelScope.launch {
                    navController.navigate(WazzabyNavigation.CAMERA_IMAGE_DETAILS)
                }
            }
        })
    }

    private fun getOutputDirectory(): File {
        val context = app.applicationContext
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, app.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
    }

    private fun handleImageCapture(file: File) {
        _screenState.value = _screenState.value.copy(
            photoFile = file
        )
    }

    fun onEvent(event: CameraEvent) {
        when (event) {
            is CameraEvent.CapturedButtonClicked -> {
                takePhoto(
                    filenameFormat = event.fileNameFormat,
                    imageCapture = event.imageCapture,
                    outputDirectory = getOutputDirectory(),
                    executor = event.executor,
                    onImageCaptured = ::handleImageCapture,
                    onError = {
                        Log.e("CapturedException", "Some Error")
                    },
                    navController = event.navController
                )
            }

            is CameraEvent.ChangeCamera -> {
                _screenState.value = _screenState.value.copy(
                    lensFacing = getLensChanged(event.lensFacing)
                )
            }

            is CameraEvent.Rotate -> {
                _screenState.value = _screenState.value.copy(
                    photoFile = screenState.value.photoFile?.let { getRotateFile(it) }
                )
                //getRotateBitmap(bitmap = screenState.value.imageCapturedUri, rotateValue = 90)
            }

            is  CameraEvent.InitElement -> {
                _screenState.value = _screenState.value.copy(
                    imageCapturedUri = null,
                    photoFile = null,
                    videoFile = null,
                    videoCaptureUriEncoded = null
                )
            }

            else -> {}
        }
    }

    private fun getLensChanged(lens: Int): Int {
        if (CameraSelector.LENS_FACING_FRONT == lens) {
            return CameraSelector.LENS_FACING_BACK
        }
        return CameraSelector.LENS_FACING_FRONT
    }

    private fun getRotateBitmap(bitmap: Bitmap, rotateValue: Int): Bitmap {
        val mat = Matrix()
        mat.postRotate(rotateValue.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, mat, true)
    }

    private fun getFileByBitmap(file: File) : Bitmap {
        val bytes: ByteArray = file.readBytes()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
    }

    private fun getRotateFile(file: File): File {
        val tempBitmap = getFileByBitmap(file)
        val bitmapRotate = getRotateBitmap(tempBitmap, 90)
        val resultBitmap = filterBitmapUpsideDown(bitmapRotate)
        val outputFile = generateFile(resultBitmap)
        return outputFile
    }

}