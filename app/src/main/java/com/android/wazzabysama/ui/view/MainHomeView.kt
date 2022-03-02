package com.android.wazzabysama.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.android.wazzabysama.R
import com.android.wazzabysama.ui.model.BottomNavigationItem
import com.android.wazzabysama.ui.view.bottomnavigationviews.PrivateMessage
import com.android.wazzabysama.ui.view.bottomnavigationviews.PublicMessageView
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainHomeView(scope: CoroutineScope, drawerState: DrawerState) {
    var switch by rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        BottomNavigationItem(R.drawable.baseline_question_answer_24, "Public Message","view_public_message"),
        BottomNavigationItem(R.drawable.baseline_chat_bubble_24, "Private Message","view_public_message")
    )

    Scaffold(topBar = { DrawerAppBar(scope, drawerState) }, bottomBar = {
        BottomNavigation {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = { Icon(painter = painterResource(id = item.id), contentDescription = null) },
                    label = { Text(item.title) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        switch = index != 1
                    }
                )
            }
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow),
            color = Color.Yellow
        ) {

            Column {
                if (switch) {
                    PublicMessageView()
                } else {
                    PrivateMessage()
                }
            }


        }
    }


}