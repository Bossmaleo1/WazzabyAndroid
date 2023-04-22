package com.android.wazzabysama.presentation.util

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun galleryDir() :  File {
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    return File(storageDir,"Wazzaby")
}

fun createPicture(): File {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "Wazzaby_${timestamp}_"
    return File.createTempFile(fileName, ".jpg", galleryDir())
}

fun generateFile(bitmap: Bitmap): File {
    val outputFile = createPicture()
    FileOutputStream(outputFile).use { fos ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, fos)
    }
    Log.d("TestingFile", "Saved file at $outputFile")
    return outputFile
}

fun getJpegFilesFromDir(dir: File) : List<File> {
    return dir.listFiles().filter { f -> f.extension == ".jpg" && f.isFile }
}