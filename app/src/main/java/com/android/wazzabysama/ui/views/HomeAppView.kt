package com.android.wazzabysama.ui.views


import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.bottomnavigationviews.PrivateMessageView
import com.android.wazzabysama.ui.views.bottomnavigationviews.PublicMessageView
import com.android.wazzabysama.ui.views.model.ConstValue
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(scope: CoroutineScope, drawerState: DrawerState, title: String, viewItem: MutableLiveData<String>, context: Any, viewModel: PublicMessageViewModel) {
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
                            text = { Text( stringResource(id = R.string.logout)) },
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

        when(saveValue) {
            ConstValue.publicMessage ->
                LazyColumn(contentPadding = innerPadding, state = listState) {
                    val problematic = Problematic(
                        81,
                        "Christianisme",
                        language = "fr",
                        icon = ""
                    )
                    viewModelPublicMessage(viewModel, problematic, 1, context,
                        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NTU1NDY4NzAsImV4cCI6MTY1NTYzMzI3MCwicm9sZXMiOlsiUk9MRV9TVVBFUl9BRE1JTiJdLCJ1c2VybmFtZSI6InNpZG5leW1hbGVvcmVnaXNAZ21haWwuY29tIn0.av76TxTxg7Apnhbdnk-loTfV4Bm8R9-sApog72XJl2oxL8kueCwUYlBsK3Gwx9RDd4fC9vpnHAbDvoo8x2h-5QRFLva63zU_YLVO5mMfoomhD8nXD7XhpTVG2zJXEdn47zslthu93HZBDjVnJTVL0rm_HTSCBiuhK8Zv06FU6dNujP8rFSgpFzurKwpaf8NdRIPxYxEExbKc6c4OFFWuGleJqM5A8Yh97Z662AO3r0zmTFyCXtNN7FMQM-FURtuwbJ68RgPb2DeUivqBE3jTHBGj6o2A4AeZC4mwceNeo4bg1A4sJOiYBye-l9wynQa7DE8REq3u5iQJkUVst4kM3PyqV_bFiGcq8GKxcU8TKa8NJUChGdi20uS3J5PuKMseuQ7uv6NrKqmIzwoYae-mZFZWKpQ7HbwkBjta8W_kIKvasj8B0hGlPVCNIuhJ_8W67k2GjxPzlR-X_QVU6IEnlrfPq00izozbbZrhKVMVqvr0sbxVK7nfiZZVVnP8stSMNzigrUC7PJVb-_Y3nUNjrX6FDy96lxg_GKS7Ut6NYiiIvjMzN3jcsNXeK4UFB5DVyU6r1_O77eV9Ypf08mO7mnUwVOe1quOvPekMeRyI95XI6XgjZfHwCGJaU1wfHIrdlXQtfKnjwNHQamZmz27OWbqNdCwveQyb1dv88E4ibRY")

                    items(count = 2000) {
                        PublicMessageView()
                    }

                    //viewModelPublicMessage(publicMessageViewModel: PublicMessageViewModel, problematic: Problematic, page: Int, context: Any)

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
fun HomeApp(scope: CoroutineScope, drawerState: DrawerState, context: Any,
            userViewModel: UserViewModel,
            publicMessageViewModel: PublicMessageViewModel) {
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
                MainHomeView(scope, drawerState, viewItem, context,publicMessageViewModel)
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                Problems(scope, drawerState,viewItem,context,publicMessageViewModel)
            }

        }
    }

}

fun viewModelPublicMessage(publicMessageViewModel: PublicMessageViewModel, problematic: Problematic, page: Int, context: Any, token: String) {
    publicMessageViewModel.getPublicMessage(problematic, page, token)
    publicMessageViewModel.publicMessageList.observe(context as LifecycleOwner) {publicMessageList->
        when (publicMessageList) {
            is Resource.Success -> {
                Log.d("Test1",publicMessageList.data?.publicMessageList.toString())
                    /*.data {
                    getUser(userViewModel, userName,
                        "Bearer $it", context
                    )
                }*/
            }

            is Resource.Error -> {
                //hideProgressBar()
                Log.d("Test1", "Erreur réseaux")
                publicMessageList.message?.let {
                    Toast.makeText(context as Context, "An error occurred : $it", Toast.LENGTH_LONG)
                        .show()
                }
            }

            is Resource.Loading -> {
                //showProgressBar()
            }
        }
    }
}
