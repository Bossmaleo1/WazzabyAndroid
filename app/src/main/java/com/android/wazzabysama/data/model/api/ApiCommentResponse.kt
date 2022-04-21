package com.android.wazzabysama.data.model.api

import com.android.wazzabysama.data.model.data.Comment
import com.google.gson.annotations.SerializedName

data class ApiCommentResponse(
    @SerializedName("hydra:member")
    val comments: List<Comment>
)