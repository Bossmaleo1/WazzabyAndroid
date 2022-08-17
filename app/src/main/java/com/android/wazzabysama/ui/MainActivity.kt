package com.android.wazzabysama.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModelFactory
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModelFactory
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import com.android.wazzabysama.ui.views.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val REQUEST_PERMISSION_LOCATION_LAST_LOCATION = 1

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var userFactory: UserViewModelFactory
    @Inject
    lateinit var publicMessageFactory: PublicMessageViewModelFactory
    private lateinit var userViewModel: UserViewModel //we call our login viewModel
    private lateinit var publicMessageViewModel: PublicMessageViewModel
    var token: String? = null

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
                        delay(100)
                        if (token === null) {
                            navController.navigate("connexion_view")
                        } else {
                            navController.navigate(WazzabyDrawerDestinations.HOME)
                        }
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]
        publicMessageViewModel =
            ViewModelProvider(this, publicMessageFactory)[PublicMessageViewModel::class.java]
    }

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

            composable(route = WazzabyDrawerDestinations.CONNEXION_VIEW) {
                Login(navController, userViewModel, context)
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = WazzabyDrawerDestinations.HOME) {
                RequestLocationPermission()
                HomeApp(
                    scope,
                    drawerState,
                    userViewModel,
                    publicMessageViewModel
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

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestLocationPermission() {
        val locationPermissionsState = rememberMultiplePermissionsState(
            listOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )

        if (locationPermissionsState.allPermissionsGranted) {
            //Text("Thanks! I can access your exact location :D")
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                locationPermissionsState.launchMultiplePermissionRequest()
            }
        }
    }

}




