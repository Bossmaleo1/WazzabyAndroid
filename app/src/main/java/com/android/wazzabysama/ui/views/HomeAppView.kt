package com.android.wazzabysama.ui.views


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.bottomnavigationviews.PrivateMessageView
import com.android.wazzabysama.ui.views.model.ConstValue
import com.android.wazzabysama.ui.views.utils.InfiniteListMessagePublicRemote
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(
    scope: CoroutineScope,
    drawerState: DrawerState,
    title: String,
    viewItem: MutableLiveData<String>,
    context: Any,
    publicMessageViewModel: PublicMessageViewModel,
    userViewModel: UserViewModel,
    listStatePublicMessage: LazyListState
) {

    val listStatePrivateMessage = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarScrollState())

    var saveValue by remember { mutableStateOf("") }
    val problematic by userViewModel.problematicValue.observeAsState()
    val user by userViewModel.userValue.observeAsState()
    val token by userViewModel.tokenValue.observeAsState()
    val publicMessageResponse by publicMessageViewModel.publicMessageListValue.observeAsState()
    val publicMessageStateList = remember { mutableStateListOf<PublicMessage>() }
    var page = 0
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SmallTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            tint = colorResource(R.color.black40),
                            contentDescription = null
                        )
                    }
                },
                actions = {

                    var expanded by remember { mutableStateOf(false) }
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { /* doSomething() */ }) {
                        //We add our badges
                        BadgedBox(badge = { Badge { Text("8") } }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                tint = colorResource(R.color.black40),
                                contentDescription = "Localized description"
                            )
                        }
                    }

                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            tint = colorResource(R.color.black40),
                            contentDescription = "Localized description"
                        )
                    }

                    //we create our Dropdown Menu Item
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.profile), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.AccountCircle,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.history), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.History,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })

                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.Settings), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle send feedback! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            },
                            trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.about), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Help,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })
                        MenuDefaults.Divider()
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.logout),color = colorResource(R.color.black40)) },
                            onClick = {
                                /* Handle settings! */

                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.PowerSettingsNew,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })

                    }
                },
                scrollBehavior = scrollBehavior,
                title = { Text(title, color = colorResource(R.color.black40)) }
            )
        }) { innerPadding ->
        var saveValue by remember { mutableStateOf("") }
        viewItem.observe(context as LifecycleOwner) {
            saveValue = it
        }

        when (saveValue) {
            ConstValue.publicMessage ->
                if (problematic !== null && token !== null && user !== null) {
                    val problematicTemp = Problematic(
                        problematic!!.id,
                        problematic!!.wording,
                        problematic!!.language,
                        problematic!!.icon
                    )
                    publicMessageViewModel.getPublicMessage(
                        problematicTemp, 1, token?.token!!
                    )


                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                           isRefreshing = true
                            page = 1
                            publicMessageViewModel.initPublicMessage()
                            publicMessageViewModel.getPublicMessage(
                                problematicTemp, page, token?.token!!
                            )
                        },
                        indicator = { state, trigger ->
                            SwipeRefreshIndicator(
                                // Pass the SwipeRefreshState + trigger through
                                state = state,
                                refreshTriggerDistance = trigger,
                                // Enable the scale animation
                                scale = true,
                                // Change the color and shape
                                backgroundColor = colorResource(R.color.Purple700),
                                shape = MaterialTheme.shapes.small,
                            )
                        }
                    ) {

                        InfiniteListMessagePublicRemote(
                            listState = listStatePublicMessage,
                            listItems = remember { publicMessageViewModel.publicMessageStateRemoteList },
                            paddingValues = PaddingValues(
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding() + 100.dp
                            )

                        ) {
                            if (publicMessageViewModel.publicMessageStateRemoteList.isNotEmpty()) {
                                if (!isRefreshing) {
                                    page++
                                    publicMessageViewModel.getPublicMessage(
                                        problematicTemp, page, token?.token!!
                                    )
                                    if (page == 1) {
                                        for (i in 0..9) {
                                            if (!publicMessageViewModel.publicMessageStateRemoteList[i].user.email.equals(user!!.email)) {
                                                userViewModel.saveUser(
                                                    UserRoom(
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.id,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.online,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.anonymous,
                                                        user!!.problematic_id,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.email,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.firstName,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.lastName,
                                                        "", "", "", "", ""
                                                    )
                                                )

                                                publicMessageViewModel.savePublicMessageRoom(
                                                    publicMessageViewModel.publicMessageStateRemoteList[i],
                                                    user!!
                                                )
                                            }
                                        }
                                    } else {
                                        val begin = (page -1)*10
                                        val end = ((page*10) - 1)

                                        for (i in begin..end) {
                                            if (!publicMessageViewModel.publicMessageStateRemoteList[i].user.email.equals(user!!.email)) {
                                                userViewModel.saveUser(
                                                    UserRoom(
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.id,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.online,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.anonymous,
                                                        user!!.problematic_id,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.email,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.firstName,
                                                        publicMessageViewModel.publicMessageStateRemoteList[i].user.lastName,
                                                        "", "", "", "", ""
                                                    )
                                                )

                                                publicMessageViewModel.savePublicMessageRoom(
                                                    publicMessageViewModel.publicMessageStateRemoteList[i],
                                                    user!!
                                                )
                                            }
                                        }
                                    }

                                }

                            }
                        }
                    }
                    // cette instruction permet de réactivé le reflesh
                    LaunchedEffect(isRefreshing) {
                        if (isRefreshing) {
                            delay(1000L)
                            isRefreshing = false
                        }
                    }

                }
            ConstValue.privateMessage ->
                LazyColumn(contentPadding = innerPadding, state = listStatePrivateMessage) {
                    items(count = 2000) {
                        PrivateMessageView()
                    }
                }
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun HomeApp(
    scope: CoroutineScope, drawerState: DrawerState, context: Any,
    userViewModel: UserViewModel,
    publicMessageViewModel: PublicMessageViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: WazzabyDrawerDestinations.HOME_ROUTE
    val viewItem: MutableLiveData<String> = MutableLiveData()
    val listStatePublicMessage = rememberLazyListState()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navController = navController,
                currentRoute = currentRoute,
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                scope,
                drawerState,
                viewItem,
                userViewModel,
                context
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = WazzabyDrawerDestinations.HOME_ROUTE
        ) {

            composable(route = WazzabyDrawerDestinations.HOME_ROUTE) {
                MainHomeView(
                    scope,
                    drawerState,
                    viewItem,
                    context,
                    publicMessageViewModel,
                    userViewModel,
                    listStatePublicMessage
                )
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(
                    scope,
                    drawerState,
                    viewItem,
                    context,
                    userViewModel,
                    publicMessageViewModel,
                    listStatePublicMessage
                )
            }

        }
    }

}