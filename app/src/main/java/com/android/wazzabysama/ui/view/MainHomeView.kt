package com.android.wazzabysama

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.android.wazzabysama.ui.model.BottomNavigationItem
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainHomeView(scope: CoroutineScope, drawerState: DrawerState) {

    var switch = true
    var maleo: Number = 0
    var switchMaleo by rememberSaveable { mutableStateOf(true) }
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
                        //switch = selectedItem === 0?true:false
                        switchMaleo = index !== 1
                        //Toast.makeText(context,"maleo sama ${maleo}",Toast.LENGTH_LONG).show()
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

            Column() {
                if(switchMaleo) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green)) {
                        Text("View 1")
                    }
                } else {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)) {
                        Text("View 2")
                    }
                }



            }



        }
    }


}