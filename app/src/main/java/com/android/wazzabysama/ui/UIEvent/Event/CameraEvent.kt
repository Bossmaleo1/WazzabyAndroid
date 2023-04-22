package com.android.wazzabysama.ui.UIEvent.Event

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.navigation.NavController
import java.util.concurrent.Executor

sealed class CameraEvent {

    data class CapturedButtonClicked(
        val fileNameFormat: String,
        val imageCapture: ImageCapture,
        val executor: Executor,
        val navController: NavController
    ): CameraEvent()

    data class ChangeCamera(
        val lensFacing: Int
    ): CameraEvent()

    object Rotate: CameraEvent()

}