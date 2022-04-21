package com.android.wazzabysama.data.model.api

import com.android.wazzabysama.data.model.data.User
import com.google.gson.annotations.SerializedName

data class ApiUserResponse(
    @SerializedName("hydra:member")
    val Users: List<User>
)