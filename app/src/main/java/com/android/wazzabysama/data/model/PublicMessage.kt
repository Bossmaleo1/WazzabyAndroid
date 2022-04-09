package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class PublicMessage (
    @SerializedName("id")
    val id: Int,
    @SerializedName("published")
    val published: String,
    @SerializedName("state")
    val state: Int,
    @SerializedName("wording")
    val wording: String,
    @SerializedName("content")
    val content: String)