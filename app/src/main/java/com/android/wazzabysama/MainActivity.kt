package com.android.wazzabysama

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WazzabySamaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
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
    fun MainView(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "launch_view" ) {
            composable("launch_view") {
                LaunchView()
            }
            composable(route = "connexion_view") {
                Connexion()
            }
        }

    }
}

