package com.android.wazzabysama.data.model.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("online")
    val online: Int?,
    @SerializedName("anonymous")
    val anonymous: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("firstName")
    val firstName: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("images")
    val images: List<Image>,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("pushNotifications")
    val pushNotifications: List<PushNotification>?,
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("username")
    val username: String?,
    @SerializedName("problematic")
    val problematic: Problematic
)