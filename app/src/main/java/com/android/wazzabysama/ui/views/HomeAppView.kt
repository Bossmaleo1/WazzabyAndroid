package com.android.wazzabysama.ui.views


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.ui.components.MenuHome
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(scope: CoroutineScope, drawerState: DrawerState, title: String) {
    //val context = LocalContext.current
    val scrollBehavior = remember { TopAppBarDefaults.pinnedScrollBehavior() }
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

                    }
                },
                scrollBehavior = scrollBehavior,
                title = { Text(title) }
            )
        }) {}

}


@Composable
@ExperimentalMaterial3Api
fun HomeApp(navController: NavHostController, scope: CoroutineScope, drawerState: DrawerState) {
    val navController2 = rememberNavController()
    val navBackStackEntry by navController2.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: WazzabyDrawerDestinations.HOME_ROUTE

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
                drawerState
            )
        }
    ) {
        NavHost(
            navController = navController2,
            startDestination = WazzabyDrawerDestinations.HOME_ROUTE
        ) {

            composable(route = WazzabyDrawerDestinations.HOME_ROUTE) {
                MainHomeView(scope, drawerState)
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(scope, drawerState)
            }

        }
    }

}
