package com.android.wazzabysama.data.model.data


import com.google.gson.annotations.SerializedName

data class Town (
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("isoCode")
    val isoCode: String)