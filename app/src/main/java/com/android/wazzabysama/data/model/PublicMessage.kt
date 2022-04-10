package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class PublicMessage(
    @SerializedName("id")
    val id: Int,
    @SerializedName("anonymous")
    val anonymous: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("published")
    val published: Date,
    @SerializedName("state")
    val state: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("wording")
    val wording: String
)