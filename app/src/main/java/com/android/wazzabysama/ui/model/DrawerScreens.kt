package com.android.wazzabysama.ui.model

sealed class DrawerScreens(val title: String, val route: String) {
    object Home : DrawerScreens("Home", "home_public_message")
    object Problem : DrawerScreens("Problem", "problem")
}