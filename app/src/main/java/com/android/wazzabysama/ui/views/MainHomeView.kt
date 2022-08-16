package com.android.wazzabysama.ui.views


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.model.BottomNavigationItem
import com.android.wazzabysama.ui.views.model.ConstValue
import kotlinx.coroutines.CoroutineScope

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun MainHomeView(
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewItem: MutableLiveData<String>,
    publicMessageViewModel: PublicMessageViewModel,
    userViewModel: UserViewModel
) {
    var switch by rememberSaveable { mutableStateOf(true) }
    var selectedItem by remember { mutableStateOf(0) }
    //This variable help use to dynamic our extended button
    var fabExtended by remember { mutableStateOf(true) }
    val listStatePublicMessage = rememberLazyListState()

    viewItem.value = ConstValue.publicMessage
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
            listStatePublicMessage
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
                        label = { Text(
                            text = remember {item.title})},
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

                ExtendedFloatingActionButton(
                    icon = { Icon(Icons.Outlined.ModeEdit, "") },
                    expanded = fabExtended,
                    text = {
                        Text(text = stringResource(R.string.new_message),
                            style = MaterialTheme.typography.titleSmall)
                    },
                    onClick = {/*do something*/ },
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                )
            }
        }) {}
}