package com.android.wazzabysama

import android.app.Application
import com.android.wazzabysama.presentation.util.galleryDir
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WazzabySamaApp: Application() {

    val galleryDir by lazy {
        galleryDir()
    }
    override fun onCreate() {
        super.onCreate()
        // Apply dynamic color
        //DynamicColors.applyToActivitiesIfAvailable
        DynamicColors.applyToActivitiesIfAvailable(this, R.style.AppTheme_Overlay)

        if(!galleryDir().exists()) {
            galleryDir.mkdirs()
        }
    }
}