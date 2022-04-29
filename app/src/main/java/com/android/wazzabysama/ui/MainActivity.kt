package com.android.wazzabysama.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.presentation.viewModel.UserViewModel
import com.android.wazzabysama.presentation.viewModel.UserViewModelFactory
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import com.android.wazzabysama.ui.views.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
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
    lateinit var userViewModel: UserViewModel //we call our login viewModel

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WazzabySamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    MainView(navController, this)
                    //userViewModel.getToken("userName", "password")

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(100)
                        navController.navigate("connexion_view")
                    }
                }
            }
        }
    }

    private fun initViewModel() {
        userViewModel = ViewModelProvider(this, userFactory)[UserViewModel::class.java]
    }

    @Composable
    @ExperimentalMaterial3Api
    fun MainView(navController: NavHostController, context: Any) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        //We call our init view model method
        this.initViewModel()
        NavHost(navController = navController, startDestination = "launch_view" ) {
            composable(route = "launch_view") {
                LaunchView()
            }

            composable(route = WazzabyDrawerDestinations.CONNEXION_VIEW) {
                Login(navController,userViewModel, context)
            }

            composable(route = WazzabyDrawerDestinations.HOME) {
                HomeApp(navController,scope, drawerState)
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

