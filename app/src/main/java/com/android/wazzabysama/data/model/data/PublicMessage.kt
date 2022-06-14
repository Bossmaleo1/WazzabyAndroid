package com.android.wazzabysama.data.model.data


import com.google.gson.annotations.SerializedName
import java.util.*

data class PublicMessage(
    @SerializedName("id")
    var id: Int,
    @SerializedName("anonymous")
    var anonymous: Int,
    @SerializedName("content")
    var content: String,
    @SerializedName("images")
    var images: List<Image>,
    @SerializedName("published")
    var published: Date,
    @SerializedName("state")
    var state: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("wording")
    var wording: String
)