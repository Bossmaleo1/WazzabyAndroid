package com.android.wazzabysama.presentation.viewModel.gallery

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.wazzabysama.WazzabySamaApp
import com.android.wazzabysama.presentation.util.galleryDir
import com.android.wazzabysama.presentation.util.getJpegFilesFromDir
import java.io.File
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app)  {

    private val pictures = mutableListOf<File>()

    fun loadPictures() {
        val files = getJpegFilesFromDir(galleryDir())
        pictures.clear()
        for(file in files) {
            pictures.add(file)
        }
    }
}