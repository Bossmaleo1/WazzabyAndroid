package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class ApiPublicMessageResponse(
    @SerializedName("hydra:member")
    val publicMessageList: List<PublicMessage>
)