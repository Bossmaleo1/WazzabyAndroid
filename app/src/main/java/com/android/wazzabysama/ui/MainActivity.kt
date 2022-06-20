package com.android.wazzabysama.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
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
                    MainView(navController, this)
                    userViewModel.getSavedToken().observe(this as LifecycleOwner) {token->
                        this.token = token?.token
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(100)
                        if(token === null) {
                            navController.navigate("connexion_view")
                        }else {
                            navController.navigate(WazzabyDrawerDestinations.HOME)
                        }
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]
        publicMessageViewModel = ViewModelProvider(this, publicMessageFactory)[PublicMessageViewModel::class.java]
    }

    @Composable
    @ExperimentalMaterial3Api
    fun MainView(navController: NavHostController, context: Any) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val activity = (LocalContext.current as? Activity)
        //We call our init view model method
        this.initViewModel()
        /*val publicMessageList = remember { mutableStateListOf<PublicMessageRoom>() }

        userViewModel.getSavedToken().observe(context as LifecycleOwner) {tokenRoom ->
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
                            publicMessageViewModel.getAllPublicMessage(problematic).observe(context) { publicMessageRoom ->
                                publicMessageList.addAll(publicMessageRoom)
                            }

                            viewModelPublicMessage(publicMessageViewModel, problematic,
                                1,context,tokenRoom.token,userRoom,userViewModel)
                        }
                }
            }
        }*/

        NavHost(navController = navController, startDestination = "LAUNCH_VIEW" ) {
            composable(route = "LAUNCH_VIEW") {
                LaunchView()
            }

            composable(route = WazzabyDrawerDestinations.CONNEXION_VIEW) {
                Login(navController,userViewModel, context)
                BackHandler {
                    activity?.finish()
                }
            }

            composable(route = WazzabyDrawerDestinations.HOME) {
                HomeApp(/*navController,*/scope, drawerState, context,  userViewModel, publicMessageViewModel)
                BackHandler {
                    activity?.finish()
                }
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

