package com.android.wazzabysama.ui.UIEvent.ScreenState.PublicMessageScreenState

import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom

data class PublicMessageScreenState (
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = false,
    var isNetworkError: Boolean = false,
    var currentPage: Int = 1,
    var initCall: Int = 0,
    var publicMessageList: MutableList<PublicMessage> = mutableListOf(),
    var publicMessageListRoom: MutableList<PublicMessageRoom> = mutableListOf(),
    var publicMessageListTemp: MutableList<PublicMessage> = mutableListOf()
)