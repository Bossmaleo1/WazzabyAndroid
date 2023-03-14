package com.android.wazzabysama.ui.UIEvent

sealed class UIEvent {
    data class ShowMessage(val message: String): UIEvent()
}
