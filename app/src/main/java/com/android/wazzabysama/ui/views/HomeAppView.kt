package com.android.wazzabysama.ui.views


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.presentation.viewModel.UserViewModel
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.bottomnavigationviews.PrivateMessageView
import com.android.wazzabysama.ui.views.bottomnavigationviews.PublicMessageView
import com.android.wazzabysama.ui.views.model.ConstValue
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(scope: CoroutineScope, drawerState: DrawerState, title: String, viewItem: MutableLiveData<String>, context: Any) {
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
    val listState = rememberLazyListState()
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
                            text = { Text("Profil") },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.AccountCircle,
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text("Historique") },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.History,
                                    contentDescription = null
                                )
                            })

                        DropdownMenuItem(
                            text = { Text("Paramètres") },
                            onClick = { /* Handle send feedback! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Settings,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = { Text("F11", textAlign = TextAlign.Center) })
                        DropdownMenuItem(
                            text = { Text("A propos") },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Help,
                                    contentDescription = null
                                )
                            })
                        MenuDefaults.Divider()
                        DropdownMenuItem(
                            text = { Text("Déconnexion") },
                            onClick = { /* Handle settings! */ },
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

        when(saveValue) {
            ConstValue.publicMessage ->
                LazyColumn(contentPadding = innerPadding, state = listState) {
                    items(count = 2000) {
                        PublicMessageView()
                    }

                    /*items(count = 3) {
                        repeat(3) {
                            PublicMessageShimmer()
                        }
                    }*/
                }
            ConstValue.privateMessage ->
                LazyColumn(contentPadding = innerPadding, state = listState) {
                    items(count = 2000) {
                        PrivateMessageView()
                    }
                }
        }

    }

}


@Composable
@ExperimentalMaterial3Api
fun HomeApp(navController: NavHostController, scope: CoroutineScope, drawerState: DrawerState, context: Any,  userViewModel: UserViewModel) {
    val navController2 = rememberNavController()
    val navBackStackEntry by navController2.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: WazzabyDrawerDestinations.HOME_ROUTE
    val viewItem: MutableLiveData<String> = MutableLiveData()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navController = navController2,
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
            navController = navController2,
            startDestination = WazzabyDrawerDestinations.HOME_ROUTE
        ) {

            composable(route = WazzabyDrawerDestinations.HOME_ROUTE) {
                MainHomeView(scope, drawerState, viewItem, context)
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(scope, drawerState,viewItem,context)
            }

        }
    }

}
