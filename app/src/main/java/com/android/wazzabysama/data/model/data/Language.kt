package com.android.wazzabysama.data.model.data

import com.google.gson.annotations.SerializedName

data class Language (
    @SerializedName("id")
    val id: String,
    @SerializedName("locale")
    val locale: String
)