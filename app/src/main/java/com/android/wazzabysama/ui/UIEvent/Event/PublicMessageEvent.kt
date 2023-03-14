package com.android.wazzabysama.ui.UIEvent.Event

import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.UserRoom

sealed class PublicMessageEvent {
    object IsNetworkConnected: PublicMessageEvent()
    object IsNetworkError: PublicMessageEvent()
    object InitPublicMessageState: PublicMessageEvent()
    data class SavePublicMessageRoom(val publicMessage: PublicMessage, val user: UserRoom): PublicMessageEvent()
    data class GetAllPublicMessage(val problematic: Problematic): PublicMessageEvent()
}
