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
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModel
import com.android.wazzabysama.presentation.viewModel.camera.CameraViewModelFactory
import com.android.wazzabysama.presentation.viewModel.drop.DropViewModel
import com.android.wazzabysama.presentation.viewModel.drop.DropViewModelFactory
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModelFactory
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.presentation.viewModel.user.UserViewModelFactory
import com.android.wazzabysama.ui.components.WazzabyNavigation
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import com.android.wazzabysama.ui.views.*
import com.android.wazzabysama.ui.views.bottomnavigationviews.publicmessage.newPublicMessage.NewPublicMessage
import com.android.wazzabysama.ui.views.camera.CameraUI
import com.android.wazzabysama.ui.views.camera.ImageDetails
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

    @Inject
    lateinit var cameraFactory: CameraViewModelFactory
    private lateinit var userViewModel: UserViewModel //we call our login viewModel
    private lateinit var publicMessageViewModel: PublicMessageViewModel
    private lateinit var dropViewModel: DropViewModel
    private lateinit var cameraViewModel: CameraViewModel
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
                        delay(200)
                        navController.navigate(WazzabyNavigation.PUBLIC_NEW_MESSAGE)
                        /*if (token === null) {
                            navController.navigate(WazzabyDrawerDestinations.LOGIN)
                        } else {
                            navController.navigate(WazzabyDrawerDestinations.HOME)
                        }*/
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]
        publicMessageViewModel =
            ViewModelProvider(this, publicMessageFactory)[PublicMessageViewModel::class.java]
        dropViewModel = ViewModelProvider(this, dropFactory)[DropViewModel::class.java]
        cameraViewModel = ViewModelProvider(this, cameraFactory)[CameraViewModel::class.java]
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

        NavHost(navController = navController, startDestination = WazzabyNavigation.LAUNCH_VIEW) {
            composable(route = WazzabyNavigation.LAUNCH_VIEW) {
                LaunchView()
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = WazzabyNavigation.LOGIN) {
                Login(navController, userViewModel, context)
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = WazzabyNavigation.HOME) {
                HomeApp(
                    scope,
                    drawerState,
                    userViewModel,
                    publicMessageViewModel,
                    dropViewModel,
                    navController
                )
            }

            composable(route = WazzabyNavigation.INSCRIPTION_FIRST) {
                FormStepFirstView(navController)
            }

            composable(route = WazzabyNavigation.INSCRIPTION_SECOND) {
                FormStepSecondView(navController)
            }

            composable(route = WazzabyNavigation.INSCRIPTION_DONE) {
                FormStepDoneView(navController)
            }

            composable(route = WazzabyNavigation.PUBLIC_NEW_MESSAGE) {
                NewPublicMessage(
                    navController
                )
            }

            composable(route = WazzabyNavigation.CAMERA) {
                CameraUI(
                    navController,
                    cameraViewModel = cameraViewModel
                )
            }

            composable(route = WazzabyNavigation.CAMERA_IMAGE_DETAILS) {
                ImageDetails(
                    navController,
                    cameraViewModel = cameraViewModel
                )
            }
        }
    }

}




