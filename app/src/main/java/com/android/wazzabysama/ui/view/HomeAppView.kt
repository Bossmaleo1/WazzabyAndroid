package com.android.wazzabysama

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.view.AppDrawer
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope

@Composable
fun DrawerAppBar(scope: CoroutineScope, drawerState: DrawerState) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { drawerState.open()}
            }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        title = { Text("") }
    )
}


@Composable
fun HomeApp(navController: NavHostController,scope: CoroutineScope, drawerState: DrawerState) {
    val navController2 = rememberNavController()
    val navBackStackEntry by navController2.currentBackStackEntryAsState()
    val currentRoute =
        navBackStackEntry?.destination?.route ?: WazzabyDrawerDestinations.HOME_ROUTE
    ModalDrawer(
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
    ){
       NavHost(
            navController = navController2,
            startDestination = WazzabyDrawerDestinations.HOME_ROUTE
        ) {

            composable(route = WazzabyDrawerDestinations.HOME_ROUTE) {

            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
            }

        }
    }

}
