package com.android.wazzabysama.data.model.data

import com.android.wazzabysama.data.model.data.User
import com.google.gson.annotations.SerializedName
import java.util.*

data class Comment(
    @SerializedName("anonymous")
    val anonymous: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("published")
    val published: Date,
    @SerializedName("user")
    val user: User
)