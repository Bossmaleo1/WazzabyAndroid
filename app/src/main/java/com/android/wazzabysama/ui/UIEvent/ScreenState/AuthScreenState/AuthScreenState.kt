package com.android.wazzabysama.ui.UIEvent.ScreenState.AuthScreenState

import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.Token
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom

data class AuthScreenState (
    var isNetworkConnected: Boolean = true,
    var isNetworkError: Boolean = false,
    var isLoad: Boolean = false,
    var currentPage: Int = 1,
    var initCallToken: Int = 0,
    var initCallUser: Int = 0,
    var user: List<User> = mutableListOf(),
    var userRoom: List<UserRoom> = mutableListOf(),
    var token: List<Token> = mutableListOf(),
    var tokenRoom: List<TokenRoom> = mutableListOf(),
    var problematic: List<Problematic> = mutableListOf(),
    var problematicRoom: List<ProblematicRoom> = mutableListOf(),
    var emailInputValue: String = "",
    var passwordInputValue: String = "",
)