package com.android.wazzabysama.data.model.data

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageName")
    val imageName: String
)