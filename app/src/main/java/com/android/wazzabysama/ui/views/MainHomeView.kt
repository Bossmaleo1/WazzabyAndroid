package com.android.wazzabysama.ui.views


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.viewModel.drop.DropViewModel
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.android.wazzabysama.ui.model.BottomNavigationItem
import com.android.wazzabysama.ui.views.model.ConstValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterial3Api
fun MainHomeView(
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewItem: MutableLiveData<String>,
    publicMessageViewModel: PublicMessageViewModel,
    userViewModel: UserViewModel,
    dropViewModel: DropViewModel,
    listStatePublicMessage: LazyListState,
    listStatePrivateMessage: LazyListState,
    navController: NavHostController,
    navController0: NavHostController
) {
    var switch by rememberSaveable { mutableStateOf(true) }
    var selectedItem by rememberSaveable { mutableStateOf(0) }
    //This variable help use to dynamic our extended button
    var fabExtended by rememberSaveable { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    if (viewItem.value.isNullOrBlank()) {
        viewItem.value = ConstValue.publicMessage
    } else if (viewItem.value.equals(ConstValue.privateMessage)) {
        switch = false
        selectedItem = 1
    } else if (viewItem.value.equals(ConstValue.publicMessage)) {
        switch = true
        selectedItem = 2
    }

    val items = listOf(
        BottomNavigationItem(
            Icons.Outlined.QuestionAnswer,
            stringResource(R.string.public_message),
            ConstValue.publicMessageRoute
        ),
        BottomNavigationItem(
            Icons.Outlined.ChatBubbleOutline,
            stringResource(R.string.private_message),
            ConstValue.publicMessageRoute
        )
    )

    Scaffold(topBar = {
        DrawerAppBar(
            scope,
            drawerState,
            viewItem,
            publicMessageViewModel,
            userViewModel,
            dropViewModel,
            listStatePublicMessage,
            listStatePrivateMessage,
            navController,
            navController0
        )
    },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.id,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = remember { item.title }
                            )
                        },
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
                //This function help us to make our button extensible
                LaunchedEffect(listStatePublicMessage) {
                    var prev = 0
                    snapshotFlow { listStatePublicMessage.firstVisibleItemIndex }
                        .collect {
                            fabExtended = it <= prev
                            prev = it
                        }
                }
                //We update our viewItem
                viewItem.value = ConstValue.publicMessage
                selectedItem = 0

                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Outlined.ModeEdit, "") },
                    expanded = fabExtended,
                    text = {
                        Text(text = stringResource(R.string.new_message),
                            style = MaterialTheme.typography.titleSmall)
                    },
                    onClick = {
                        /*coroutineScope.launch {
                            listStatePublicMessage.animateScrollToItem(0)
                        }*/
                        navController0.navigate(WazzabyNavigation.PUBLIC_NEW_MESSAGE)
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            } else {
                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Outlined.ChatBubbleOutline, "") },
                    expanded = false,
                    text = {
                        Text(
                            text = stringResource(R.string.new_message),
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            listStatePublicMessage.animateScrollToItem(0)
                        }
                    },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            }

        }) {}
}