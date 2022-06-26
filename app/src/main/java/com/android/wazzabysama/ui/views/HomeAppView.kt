package com.android.wazzabysama.ui.views


import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.bottomnavigationviews.PrivateMessageView
import com.android.wazzabysama.ui.views.bottomnavigationviews.PublicMessageView
import com.android.wazzabysama.ui.views.model.ConstValue
import com.android.wazzabysama.ui.views.shimmer.PublicMessageShimmer
import com.android.wazzabysama.ui.views.utils.InfiniteListHandler
import com.android.wazzabysama.ui.views.utils.InfiniteListMessagePublicRemote
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(
    scope: CoroutineScope, drawerState: DrawerState,
    title: String, viewItem: MutableLiveData<String>, context: Any,
    publicViewModel: PublicMessageViewModel, userViewModel: UserViewModel
) {
    val listStatePublicMessage = rememberLazyListState()
    val listStatePrivateMessage = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarScrollState())

    var saveValue by remember { mutableStateOf("") }
    val problematic by userViewModel.problematicValue.observeAsState()
    val user by userViewModel.userValue.observeAsState()
    val token by userViewModel.tokenValue.observeAsState()
    val publicMessageResponse by publicViewModel.publicMessageListValue.observeAsState()
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
                        Icon(Icons.Filled.Menu, contentDescription = null)
                    }
                },
                actions = {

                    var expanded by remember { mutableStateOf(false) }
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = { /* doSomething() */ }) {
                        //We add our badges
                        BadgedBox(badge = { Badge { Text("8") } }) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = "Localized description"
                            )
                        }
                    }

                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Localized description"
                        )
                    }

                    //we create our Dropdown Menu Item
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.profile)) },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.AccountCircle,
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.history)) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.History,
                                    contentDescription = null
                                )
                            })

                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.Settings)) },
                            onClick = { /* Handle send feedback! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.about)) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Help,
                                    contentDescription = null
                                )
                            })
                        MenuDefaults.Divider()
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.logout)) },
                            onClick = {
                                /* Handle settings! */

                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.PowerSettingsNew,
                                    contentDescription = null
                                )
                            })

                    }
                },
                scrollBehavior = scrollBehavior,
                title = { Text(title) }
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
                    publicViewModel.getPublicMessage(
                        problematicTemp, 1, token?.token!!
                    )


                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                           isRefreshing = true
                            page = 1
                            publicViewModel.initPublicMessage()
                            publicViewModel.getPublicMessage(
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
                            listItems = remember { publicViewModel.publicMessageStateRemoteList },
                            paddingValues = PaddingValues(
                                top = innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding() + 100.dp
                            )

                        ) {
                            if (publicViewModel.publicMessageStateRemoteList.isNotEmpty()) {
                                if (!isRefreshing) {
                                    page++
                                    publicViewModel.getPublicMessage(
                                        problematicTemp, page, token?.token!!
                                    )
                                    if (page == 1) {
                                        for (i in 0..9) {
                                            if (!publicViewModel.publicMessageStateRemoteList[i].user.email.equals(user!!.email)) {
                                                userViewModel.saveUser(
                                                    UserRoom(
                                                        publicViewModel.publicMessageStateRemoteList[i].user.id,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.online,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.anonymous,
                                                        user!!.problematic_id,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.email,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.firstName,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.lastName,
                                                        "", "", "", "", ""
                                                    )
                                                )

                                                publicViewModel.savePublicMessageRoom(
                                                    publicViewModel.publicMessageStateRemoteList[i],
                                                    user!!
                                                )
                                            }
                                        }
                                    } else {
                                        val begin = (page -1)*10
                                        val end = ((page*10) - 1)

                                        for (i in begin..end) {
                                            if (!publicViewModel.publicMessageStateRemoteList[i].user.email.equals(user!!.email)) {
                                                userViewModel.saveUser(
                                                    UserRoom(
                                                        publicViewModel.publicMessageStateRemoteList[i].user.id,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.online,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.anonymous,
                                                        user!!.problematic_id,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.email,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.firstName,
                                                        publicViewModel.publicMessageStateRemoteList[i].user.lastName,
                                                        "", "", "", "", ""
                                                    )
                                                )

                                                publicViewModel.savePublicMessageRoom(
                                                    publicViewModel.publicMessageStateRemoteList[i],
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
                    userViewModel
                )
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(
                    scope,
                    drawerState,
                    viewItem,
                    context,
                    userViewModel,
                    publicMessageViewModel
                )
            }

        }
    }

}