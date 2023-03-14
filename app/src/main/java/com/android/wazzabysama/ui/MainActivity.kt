package com.android.wazzabysama.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.presentation.viewModel.drop.DropViewModel
import com.android.wazzabysama.presentation.viewModel.drop.DropViewModelFactory
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModelFactory
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModelFactory
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import com.android.wazzabysama.ui.views.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userFactory: UserViewModelFactory
    @Inject
    lateinit var publicMessageFactory: PublicMessageViewModelFactory
    @Inject
    lateinit var dropFactory: DropViewModelFactory
    private lateinit var userViewModel: UserViewModel //we call our login viewModel
    private lateinit var publicMessageViewModel: PublicMessageViewModel
    private lateinit var dropViewModel: DropViewModel
    var token: String? = null

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WazzabySamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    val listStatePublicMessage = rememberLazyListState()
                    val listStatePrivateMessage = rememberLazyListState()

                    MainView(
                        navController,
                        this,
                        listStatePublicMessage,
                        listStatePrivateMessage)
                    userViewModel.getSavedToken().observe(this as LifecycleOwner) { token ->
                        this.token = token?.token
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(200)
                        if (token === null) {
                            navController.navigate(WazzabyDrawerDestinations.LOGIN)
                        } else {
                            navController.navigate(WazzabyDrawerDestinations.HOME)
                        }
                    }
                }
            }
        }

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]
        publicMessageViewModel =
            ViewModelProvider(this, publicMessageFactory)[PublicMessageViewModel::class.java]
        dropViewModel = ViewModelProvider(this, dropFactory)[DropViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    @ExperimentalMaterial3Api
    fun MainView(
        navController: NavHostController,
        context: Any,
        listStatePublicMessage: LazyListState,
        listStatePrivateMessage: LazyListState
    ) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val activity = (LocalContext.current as? Activity)
        //We call our init view model method
        this.initViewModel()

        NavHost(navController = navController, startDestination = WazzabyDrawerDestinations.LAUNCH_VIEW) {
            composable(route = WazzabyDrawerDestinations.LAUNCH_VIEW) {
                LaunchView()
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = WazzabyDrawerDestinations.LOGIN) {
                Login(navController, userViewModel, context)
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = WazzabyDrawerDestinations.HOME) {
                HomeApp(
                    scope,
                    drawerState,
                    userViewModel,
                    publicMessageViewModel,
                    dropViewModel,
                    navController
                )
                /*BackHandler {
                    activity?.finish()
                }*/
            }

            composable(route = WazzabyDrawerDestinations.INSCRIPTION_FIRST) {
                FormStepFirstView(navController)
            }

            composable(route = WazzabyDrawerDestinations.INSCRIPTION_SECOND) {
                FormStepSecondView(navController)
            }

            composable(route = WazzabyDrawerDestinations.INSCRIPTION_DONE) {
                FormStepDoneView(navController)
            }
        }
    }

}




