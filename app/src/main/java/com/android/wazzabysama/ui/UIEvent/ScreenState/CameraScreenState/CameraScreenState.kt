package com.android.wazzabysama.ui.UIEvent.ScreenState.CameraScreenState

import android.net.Uri
import androidx.camera.core.CameraSelector
import java.io.File
import java.net.URLEncoder

data class CameraScreenState (
    var imageCapturedUri: Uri? = null,
    var videoCaptureUriEncoded: Uri? = null,
    var videoFile: File? = null,
    var lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    var photoFile: File? = null
)