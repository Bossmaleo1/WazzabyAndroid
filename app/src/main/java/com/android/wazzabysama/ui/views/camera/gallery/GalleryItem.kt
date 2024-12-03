package com.android.wazzabysama.ui.views.camera.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.android.wazzabysama.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import java.io.File

@ExperimentalMaterial3Api
@Composable
fun GalleryItem(photoUri : String) {
    val imgFile = File(photoUri)
    Column {
        Image(
            painter = rememberImagePainter(data = imgFile),
            contentDescription = "Profile picture description",
            modifier = Modifier
                .padding(4.dp)
                .height(100.dp)
                .width(100.dp)
        )
    }
}