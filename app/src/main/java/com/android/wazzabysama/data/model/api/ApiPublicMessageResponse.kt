package com.android.wazzabysama.data.model.api

import com.android.wazzabysama.data.model.data.PublicMessage
import com.google.gson.annotations.SerializedName

data class ApiPublicMessageResponse(
    @SerializedName("hydra:member")
    val publicMessageList: List<PublicMessage>
)