package com.android.wazzabysama.data.model

import com.google.gson.annotations.SerializedName

data class ApiCommentResponse(
    @SerializedName("hydra:member")
    val comments: List<Comment>
)