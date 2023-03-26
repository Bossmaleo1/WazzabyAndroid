package com.android.wazzabysama.ui.UIEvent.ScreenState.PublicMessageScreenState

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom

data class PublicMessageScreenState (
    var isNetworkConnected: Boolean = true,
    var isLoad: Boolean = false,
    var isNetworkError: Boolean = false,
    var currentPage: Int = 1,
    var initCall: Int = 0,
    var publicMessageList: SnapshotStateList<PublicMessage> = mutableStateListOf(),
    var publicMessageListRoom: SnapshotStateList<PublicMessageRoom> = mutableStateListOf(),
    var publicMessageListTemp: SnapshotStateList<PublicMessage> = mutableStateListOf()
)