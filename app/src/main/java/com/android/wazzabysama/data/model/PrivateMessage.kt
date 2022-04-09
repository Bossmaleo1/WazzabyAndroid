package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class PrivateMessage (
    @SerializedName("id")
    val id: Int,
    @SerializedName("content")
    val content: String,
    @SerializedName("published")
    val published: String,
    @SerializedName("readDate")
    val readDate: String,
    @SerializedName("readMention")
    val readMention: Int,
    @SerializedName("receiverAnonymous")
    val receiverAnonymous: Int,
    @SerializedName("transmitterAnonymous")
    val transmitterAnonymous: Int)