package com.android.wazzabysama

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.ui.model.DrawerScreens
import com.android.wazzabysama.ui.view.PublicMessage
import kotlinx.coroutines.CoroutineScope

@Composable
fun DrawerAppBar(scope: CoroutineScope, drawerState: DrawerState) {
    /*val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current*/
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { drawerState.open()}
            }) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        },
        title = { Text("") }
    )
}


@Composable
fun HomeApp(navController: NavHostController,scope: CoroutineScope, drawerState: DrawerState) {
    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {

            Spacer(Modifier.size(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Column(modifier = Modifier
                    .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile),
                        modifier = Modifier.size(72.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Profile picture description"
                    )

                    Spacer(Modifier.size(20.dp))

                    Text(text ="Sidney MALEO")

                    Spacer(Modifier.size(20.dp))

                    Divider(
                        color = colorResource(R.color.primary_gray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        val checkedState = remember { mutableStateOf(true) }
                        Switch(
                            checked = checkedState.value,
                            onCheckedChange = { checkedState.value = it },
                        )
                    }

                    Divider(
                        color = colorResource(R.color.primary_gray),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(3.dp)
                    )

                    Spacer(Modifier.size(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_edit_note_black_24),
                            contentDescription = "",
                            tint = colorResource(R.color.colorPrimary)
                        )
                        Text(text = "Home")
                    }

                    Spacer(Modifier.size(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_forward_black_24),
                            contentDescription = "",
                            tint = colorResource(R.color.colorPrimary)
                        )
                        Text(text = "Problem")
                    }
                }

           }


        }
    ){
        NavHost(
            navController = rememberNavController(),
            startDestination = DrawerScreens.Home.route
        ) {

            composable(route = "home_public_message") {
                MainHomeView(scope,drawerState)
            }

            composable(route = "view_public_message") {
                PublicMessage()
            }

        }
    }


}
