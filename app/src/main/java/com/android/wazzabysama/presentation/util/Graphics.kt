package com.android.wazzabysama.presentation.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF

fun filterBitmapUpsideDown(bitmap: Bitmap):Bitmap {
    val resultBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
    val canvas = Canvas(resultBitmap)

    val croppedBitmap = Bitmap.createBitmap(
        bitmap,
        0,0,
        bitmap.width, bitmap.height/2
    )

    canvas.drawBitmap(croppedBitmap, 0f, 0f, null)

    val halfSize = RectF(0f,0f,bitmap.width.toFloat(),bitmap.height/2.0f)
    val matrix = Matrix().apply {
        postScale(1f, -1f, halfSize.centerX(), halfSize.centerY())
        postTranslate(0f, halfSize.height())
    }

    val paint = Paint().apply {
        colorFilter = LightingColorFilter(Color.rgb(40,84,140),0)
    }

    canvas.drawBitmap(croppedBitmap, matrix, paint)

    return resultBitmap
}