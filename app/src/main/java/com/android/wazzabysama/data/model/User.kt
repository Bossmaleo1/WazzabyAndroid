package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    val id: Int,
    @SerializedName("anonymous")
    val anonymous: Int,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String)