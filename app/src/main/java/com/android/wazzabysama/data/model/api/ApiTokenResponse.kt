package com.android.wazzabysama.data.model.api

import com.google.gson.annotations.SerializedName

data class ApiTokenResponse(
    @SerializedName("token")
    val token: String
)