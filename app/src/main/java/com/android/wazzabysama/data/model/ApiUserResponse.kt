package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class ApiUserResponse(
    @SerializedName("hydra:member")
    val Users: List<User>
)