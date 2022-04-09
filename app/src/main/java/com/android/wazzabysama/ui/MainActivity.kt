package com.android.wazzabysama.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import com.android.wazzabysama.ui.views.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WazzabySamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    MainView(navController)
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(100)
                        navController.navigate("connexion_view")
                    }
                }
            }
        }
    }

    @Composable
    @ExperimentalMaterial3Api
    fun MainView(navController: NavHostController) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        NavHost(navController = navController, startDestination = "launch_view" ) {
            composable("launch_view") {
                LaunchView()
            }

            composable(route = WazzabyDrawerDestinations.CONNEXION_VIEW) {
                Connexion(navController)
            }

            composable(route = WazzabyDrawerDestinations.HOME) {
                HomeApp(navController,scope, drawerState)
            }

            /*composable(route = WazzabyDrawerDestinations.HOME_ROUTE) {
                MainHomeView(scope,drawerState)
            }

            composable(route = WazzabyDrawerDestinations.PROBLEM_ROUTE) {

            }*/

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

