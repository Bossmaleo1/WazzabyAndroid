package com.android.wazzabysama.data.model.data

import com.google.gson.annotations.SerializedName


data class Problematic (
    @SerializedName("id")
    val id: Int,
    @SerializedName("wording")
    val wording: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("icon")
    val icon: String)
