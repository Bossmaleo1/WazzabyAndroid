package com.android.wazzabysama.ui.UIEvent.Event

sealed class AuthEvent {
    data class GetToken(val userName: String, val password: String): AuthEvent()
    data class GetUser(val userName: String, val token: String): AuthEvent()
    object GetSavedProblematic: AuthEvent()
    object GetSavedUserByToken: AuthEvent()
    object GetSavedToken: AuthEvent()
    object InitUserState: AuthEvent()
    object IsNetworkConnected: AuthEvent()
    object ConnectionAction: AuthEvent()
    object IsNetworkError: AuthEvent()
    object IsEmptyField: AuthEvent()
    data class IsInitField(val email: String, val password: String): AuthEvent()
    data class EmailValueEntered(val value: String): AuthEvent()
    data class PasswordValueEntered(val value: String): AuthEvent()
}
