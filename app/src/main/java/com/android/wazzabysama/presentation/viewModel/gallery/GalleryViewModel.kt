package com.android.wazzabysama.presentation.viewModel.gallery

import android.app.Application
import android.content.ContentResolver
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import com.android.wazzabysama.WazzabySamaApp
import com.android.wazzabysama.presentation.util.galleryDir
import com.android.wazzabysama.presentation.util.getJpegFilesFromDir
import java.io.File
import javax.inject.Inject

class GalleryViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app)  {

    val pictures = SnapshotStateList<File>()

    fun loadPictures() {
        val files = getJpegFilesFromDir(galleryDir())
        Log.d("MALEOSAMA939393", "MAYEKO : ${files.size}")
        pictures.clear()
        for(file in files) {
            pictures.add(file)
        }
    }

    fun getImagePath(): SnapshotStateList<String> {
        var imgList: SnapshotStateList<String> = mutableStateListOf()
        val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        if (isSDPresent) {
            // if the sd card is present we are creating a new list in
            // which we are getting our images data with their ids.
            val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
            val orderBy = MediaStore.Images.Media._ID
            val cursor: Cursor? = app.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                orderBy
            )
            val count: Int = cursor!!.getCount()
            if(cursor.moveToFirst()) {
                do {

                } while (cursor.moveToNext())
            }
            /*for (i in 0 until count) {
                if (imgList.size > 8) {
                    break
                }
                cursor!!.moveToPosition(i * 3)
                val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                imgList.add(cursor.getString(dataColumnIndex))
            }*/
            cursor!!.close()
        }
        return imgList
    }

    fun getListImages(): SnapshotStateList<String> {
        var imgList: SnapshotStateList<String> = mutableStateListOf()
        var cols = listOf<String>(MediaStore.Images.Thumbnails.DATA).toTypedArray()
        var rs = app.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            cols, null, null, null
        )
        if(rs?.moveToNext()!!) {
            rs?.getString(0)?.let { imgList.add(it) }
        }
        return imgList
    }
}