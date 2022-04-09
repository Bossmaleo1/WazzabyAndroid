package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class Notifications (
    @SerializedName("id")
    val id: Int,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("state")
    val state: Int,
    @SerializedName("id_content")
    val idContent: Int,
    @SerializedName("id_receiver")
    val idReceiver: Int,
    @SerializedName("id_type")
    val idType: Int,
    @SerializedName("anonymous")
    val anonymous: Int)