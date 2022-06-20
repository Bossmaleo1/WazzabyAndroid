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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@Composable
@ExperimentalMaterial3Api
fun DrawerAppBar(
    scope: CoroutineScope, drawerState: DrawerState,
    title: String, viewItem: MutableLiveData<String>, context: Any,
    publicViewModel: PublicMessageViewModel, userViewModel: UserViewModel
) {
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarScrollState())

    val token = userViewModel.getSavedToken()
    var saveValue by remember { mutableStateOf("") }
    val publicMessageList = remember { mutableStateListOf<PublicMessageRoom>() }


    userViewModel.getSavedToken().observe(context as LifecycleOwner) { tokenRoom ->
        userViewModel.getSavedUserByToken(tokenRoom.token)
            .observe(context as LifecycleOwner) { userRoom ->
                userRoom.id?.let {
                    userViewModel.getSavedProblematic(it.toInt())
                        .observe(context) { problematicRoom ->
                            /* val problematic = Problematic(
                                 problematicRoom.id,
                                 problematicRoom.wording,
                                 problematicRoom.language,
                                 problematicRoom.icon
                             )
                             //On commence par mettre à jour notre liste de message public
                             publicViewModel.getAllPublicMessage(problematic)
                                 .observe(context) { publicMessageRoom ->
                                     publicMessageList.addAll(publicMessageRoom)
                                 }

                             viewModelPublicMessage(
                                 publicViewModel, problematic,
                                 1, context, tokenRoom.token, userRoom, userViewModel
                             )*/
                        }
                }
            }
    }


    // we loading our publicMessage
    /*userViewModel.getSavedToken().observe(context as LifecycleOwner) {tokenRoom ->
        userViewModel.getSavedUserByToken(tokenRoom.token).observe(context as LifecycleOwner) {userRoom->
            userRoom.id?.let {
                userViewModel.getSavedProblematic(it.toInt())
                    .observe(context) {problematicRoom->
                        val problematic = Problematic(
                            problematicRoom.id,
                            problematicRoom.wording,
                            problematicRoom.language,
                            problematicRoom.icon
                        )
                        //On commence par mettre à jour notre liste de message public
                        publicViewModel.getAllPublicMessage(problematic).observe(context) { publicMessageRoom ->
                            publicMessageList.addAll(publicMessageRoom)
                        }

                        viewModelPublicMessage(publicViewModel, problematic,
                            1,context,tokenRoom.token,userRoom,userViewModel)
                    }
            }
        }
    }*/

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
                LazyColumn(contentPadding = innerPadding, state = listState) {
                    items(count = 2000) {
                        PublicMessageView()
                    }
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

    //publicMessageViewModel.getPublicMessage()


    userViewModel.getSavedToken()
    if (userViewModel.tokenState.value.isNotEmpty()) {
        if (userViewModel.tokenState.value[0] !== null) {
            userViewModel.getSavedUserByToken(userViewModel.tokenState.value[0].token)
        }

        if (userViewModel.userState.value.isNotEmpty()) {
            userViewModel.userState.value[0].id?.let { userViewModel.getSavedProblematic(it.toInt()) }
            if (userViewModel.problematicState.value.isNotEmpty()) {
                val problematic = Problematic(
                    userViewModel.problematicState.value[0].id,
                    userViewModel.problematicState.value[0].wording,
                    userViewModel.problematicState.value[0].language,
                    userViewModel.problematicState.value[0].icon
                )
                publicMessageViewModel.getPublicMessage(
                    problematic,
                    1,
                    userViewModel.tokenState.value[0].token
                )
                if (publicMessageViewModel.publicMessageList.value.isNotEmpty()) {
                    publicMessageViewModel.publicMessageList.value.forEach { publicMessage ->
                        userViewModel.userState.value[0].id?.let {
                            userViewModel.saveUser(
                                UserRoom(
                                    publicMessage.user.id,
                                    publicMessage.user.online,
                                    publicMessage.user.anonymous,
                                    it.toInt(),
                                    publicMessage.user.email,
                                    publicMessage.user.firstName,
                                    publicMessage.user.lastName,
                                    "",
                                    "",
                                    "",
                                    publicMessage.user.username,
                                    "")
                            )
                        }
                        publicMessageViewModel.savePublicMessageRoom(
                            publicMessage
                            ,userViewModel.userState.value[0]
                        )
                    }
                }
            }
        }
    }


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

/*fun viewModelPublicMessage(
    publicMessageViewModel: PublicMessageViewModel,
    problematic: Problematic,
    page: Int, context: Any,
    token: String,
    user: UserRoom,
    userViewModel: UserViewModel
) {
    publicMessageViewModel.getPublicMessage(problematic, page, token)
    publicMessageViewModel.publicMessageList.observe(context as LifecycleOwner) { publicMessageList ->
        when (publicMessageList) {
            is Resource.Success -> {
                publicMessageList.data.let { result ->

                    for (publicMessage: PublicMessage in result?.publicMessageList?.toList()!!) {
                        Log.d("Test", " ${publicMessage.content}")
                        userViewModel.saveUser(
                            UserRoom(
                                publicMessage.user.id,
                                publicMessage.user.online,
                                publicMessage.user.anonymous,
                                user.problematic_id,
                                publicMessage.user.email,
                                publicMessage.user.firstName,
                                publicMessage.user.lastName,
                                "", "", "", "", ""
                            )
                        )

                        publicMessageViewModel.savePublicMessageRoom(publicMessage, user)

                    }
                }
            }

            is Resource.Error -> {
                hideProgressBar()
                Log.d("Test1", "Erreur réseaux")
                publicMessageList.message?.let {
                    Toast.makeText(context as Context, "An error occurred : $it", Toast.LENGTH_LONG)
                        .show()
                }
            }

            is Resource.Loading -> {
                showProgressBar()
            }
        }
    }

}*/
