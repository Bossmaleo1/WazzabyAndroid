package com.android.wazzabysama.ui.views


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.android.wazzabysama.R
import com.android.wazzabysama.ui.model.BottomNavigationItem
import com.android.wazzabysama.ui.views.utils.ConstValue
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun MainHomeView(
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewItem: MutableLiveData<String>,
    context: Any
) {
    var switch by rememberSaveable { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf(0) }
    viewItem.value = ConstValue.publicMessage
    val items = listOf(
        BottomNavigationItem(
            R.drawable.baseline_question_answer_24,
            stringResource(R.string.public_message),
            ConstValue.publicMessageRoute
        ),
        BottomNavigationItem(
            R.drawable.baseline_chat_bubble_24,
            stringResource(R.string.private_message),
            ConstValue.publicMessageRoute
        )
    )



    Scaffold(topBar = {
        DrawerAppBar(scope, drawerState, "Wazzaby", viewItem, context)
    },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.id),
                                contentDescription = null
                            )
                        },
                        label = { Text(item.title) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            switch = index != 1
                            if (switch) {
                                viewItem.value = ConstValue.publicMessage
                            } else {
                                viewItem.value = ConstValue.privateMessage
                            }
                        }
                    )
                }
            }
        }, floatingActionButton = {
            if (switch) {
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Filled.ModeEdit, "") },
                    text = {
                        Text(text = "Nouveau message",
                            style = MaterialTheme.typography.titleSmall)
                    },
                    onClick = {/*do something*/ },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            }
        }) {}
}