package com.android.wazzabysama.ui.views


import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.presentation.viewModel.drop.DropViewModel
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.bottomnavigationviews.PrivateMessageView
import com.android.wazzabysama.ui.views.bottomnavigationviews.privatemessage.conversation.Conversation
import com.android.wazzabysama.ui.views.model.ConstValue
import com.android.wazzabysama.ui.views.utils.InfiniteListMessagePublicRemote
import com.android.wazzabysama.ui.views.utils.chips
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(
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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var saveValue by remember { mutableStateOf("") }
    val problematic by userViewModel.problematicValue.observeAsState()
    val user by userViewModel.userValue.observeAsState()
    val token by userViewModel.tokenValue.observeAsState()

    var expandedToolbar by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    //var page = 0
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AnimatedVisibility(
                visible = expandedToolbar,
                enter = slideInVertically {
                    // Slide in from 40 dp from the top.
                    with(density) { -40.dp.roundToPx() }
                } + expandVertically(
                    // Expand from the top.
                    expandFrom = Alignment.Top
                ) + fadeIn(
                    // Fade in with the initial alpha of 0.3f.
                    initialAlpha = 0.3f
                ),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {

                TopAppBar(
                    title = {
                        Text(
                            if (saveValue == ConstValue.problem) stringResource(R.string.problematic_app) else stringResource(
                                id = R.string.app_name
                            ), color = colorResource(R.color.black40)
                        )
                    },
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
                        //We add our badges
                        BadgedBox(badge = {
                            Badge {
                                val badgeNumber = "8"
                                Text(
                                    badgeNumber,
                                    modifier = Modifier.semantics {
                                        contentDescription = "$badgeNumber new notifications"
                                    }
                                )
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                tint = colorResource(R.color.black40),
                                contentDescription = "Localized description"
                            )
                        }
                        //}

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
                                text = {
                                    Text(
                                        stringResource(id = R.string.profile),
                                        color = colorResource(R.color.black40)
                                    )
                                },
                                onClick = { /* Handle edit! */ },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.AccountCircle,
                                        tint = colorResource(R.color.black40),
                                        contentDescription = null
                                    )
                                })
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        stringResource(id = R.string.history),
                                        color = colorResource(R.color.black40)
                                    )
                                },
                                onClick = { /* Handle settings! */ },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.History,
                                        tint = colorResource(R.color.black40),
                                        contentDescription = null
                                    )
                                })

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        stringResource(id = R.string.Settings),
                                        color = colorResource(R.color.black40)
                                    )
                                },
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
                                text = {
                                    Text(
                                        stringResource(id = R.string.about),
                                        color = colorResource(R.color.black40)
                                    )
                                },
                                onClick = { /* Handle settings! */ },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Help,
                                        tint = colorResource(R.color.black40),
                                        contentDescription = null
                                    )
                                })
                            // MenuDefaults.
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        stringResource(id = R.string.logout),
                                        color = colorResource(R.color.black40)
                                    )
                                },
                                onClick = {
                                    dropViewModel.deleteAll()
                                    navController0.navigate(WazzabyDrawerDestinations.LOGIN)
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Outlined.Logout,
                                        tint = colorResource(R.color.black40),
                                        contentDescription = null
                                    )
                                })

                        }
                    }, scrollBehavior = scrollBehavior
                )

            }

        }) { innerPadding ->
        viewItem.observe(LocalContext.current as LifecycleOwner) {
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
                    if (publicMessageViewModel.currentPage.value == 1 && !isRefreshing) {
                        publicMessageViewModel.getPublicMessage(
                            problematicTemp,
                            publicMessageViewModel.currentPage.value,
                            token?.token!!
                        )
                    }

                    LaunchedEffect(listStatePublicMessage) {
                        var prev = 0
                        snapshotFlow { listStatePublicMessage.firstVisibleItemIndex }
                            .collect {
                                expandedToolbar = it <= prev
                                prev = it
                            }
                    }


                    SwipeRefresh(
                        state = swipeRefreshState,
                        onRefresh = {
                            isRefreshing = true
                            publicMessageViewModel.currentPage.value = 1
                            publicMessageViewModel.initPublicMessage()
                        },
                        indicator = { state, trigger ->
                            // publicMessageViewModel.initPublicMessage()
                            SwipeRefreshIndicator(
                                // Pass the SwipeRefreshState + trigger through
                                state = state,
                                refreshTriggerDistance = trigger,
                                // Enable the scale animation
                                scale = true,
                                // Change the color and shape
                                backgroundColor = androidx.compose.material.MaterialTheme.colors.primary.copy(
                                    alpha = 0.08f
                                ),
                                shape = MaterialTheme.shapes.small,
                            )
                        }
                    ) {

                        Column {
                            AnimatedVisibility(
                                visible = expandedToolbar,
                                enter = slideInVertically {
                                    // Slide in from 40 dp from the top.
                                    with(density) { -40.dp.roundToPx() }
                                } + expandVertically(
                                    // Expand from the top.
                                    expandFrom = Alignment.Top
                                ) + fadeIn(
                                    // Fade in with the initial alpha of 0.3f.
                                    initialAlpha = 0.3f
                                ),
                                exit = slideOutVertically() + shrinkVertically() + fadeOut()
                            ) {
                                chips(
                                    paddingValues = PaddingValues(
                                        top = 60.dp,
                                        bottom = innerPadding.calculateBottomPadding()
                                    ), rememberLazyListState()
                                )
                            }

                            InfiniteListMessagePublicRemote(
                                listState = listStatePublicMessage,
                                listItems = remember { publicMessageViewModel.publicMessageStateRemoteList },
                                paddingValues = PaddingValues(
                                    top = 0.dp,
                                    bottom = innerPadding.calculateBottomPadding() + 100.dp
                                ),
                                publicMessageViewModel = publicMessageViewModel,
                                problematic = problematicTemp,
                                token?.token!!
                            )
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
                    expandedToolbar = true
                    items(count = 2000) {
                        PrivateMessageView(navController)
                    }
                }
        }

    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ExperimentalMaterial3Api
fun HomeApp(
    scope: CoroutineScope, drawerState: DrawerState,
    userViewModel: UserViewModel,
    publicMessageViewModel: PublicMessageViewModel,
    dropViewModel: DropViewModel,
    navController0: NavHostController
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: WazzabyDrawerDestinations.HOME_ROUTE
    val viewItem: MutableLiveData<String> = MutableLiveData()
    val listStatePublicMessage = rememberLazyListState()
    val listStatePrivateMessage = rememberLazyListState()

    //We request our location permission
    RequestLocationPermission()
    //we draw our Modal Navigation
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                AppDrawer(
                    navController = navController,
                    currentRoute = currentRoute,
                    modifier = Modifier,
                    scope,
                    drawerState,
                    viewItem,
                    userViewModel
                )
            }
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
                    publicMessageViewModel,
                    userViewModel,
                    dropViewModel,
                    listStatePublicMessage,
                    listStatePrivateMessage,
                    navController,
                    navController0
                )
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(
                    scope,
                    drawerState,
                    viewItem,
                    userViewModel,
                    publicMessageViewModel,
                    dropViewModel,
                    navController,
                    navController0
                )
            }

            composable(route = WazzabyDrawerDestinations.CONVERSATION) {
                Conversation(
                    navController,
                    viewItem
                )
            }

        }
    }

}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermission() {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    if (locationPermissionsState.allPermissionsGranted) {
        Log.d("MALEO939393", "The permission is allow")
        Timber.d("Only approximate location access granted.")
    } else {
        CoroutineScope(Dispatchers.Main).launch {
            locationPermissionsState.launchMultiplePermissionRequest()
        }


    }

    if (locationPermissionsState.shouldShowRationale) {
        Log.d("MALEO9393", " important. Please grant all of them for the app to function properly.")
    } else {
        Log.d("MALEO9393", "denied. The app cannot function without them.")
    }


}
