package com.android.wazzabysama.ui.views



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(scope: CoroutineScope, drawerState: DrawerState, title: String) {
    val context = LocalContext.current
    SmallTopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { drawerState.open()}
            }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        title = { Text(title) }
    )
}


@Composable
@ExperimentalMaterial3Api
fun HomeApp(navController: NavHostController,scope: CoroutineScope, drawerState: DrawerState) {
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
    ){
       NavHost(
            navController = navController2,
            startDestination = WazzabyDrawerDestinations.HOME_ROUTE
        ) {

            composable(route = WazzabyDrawerDestinations.HOME_ROUTE) {
                MainHomeView(scope,drawerState)
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(scope,drawerState)
            }

        }
    }

}
