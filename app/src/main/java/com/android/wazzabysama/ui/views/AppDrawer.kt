package com.android.wazzabysama.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.presentation.viewModel.UserViewModel
import com.android.wazzabysama.ui.components.NavigationIcon
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.model.ConstValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@ExperimentalMaterial3Api
fun AppDrawer(
    navController: NavHostController,
    currentRoute: String,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    drawerState: DrawerState,
    viewItem: MutableLiveData<String>,
    userViewModel: UserViewModel,
    context: Any
) {
    var user: UserRoom
    var userName by rememberSaveable { mutableStateOf("") }
    userViewModel.getSavedToken().observe(context as LifecycleOwner) {token->
        userViewModel.getSavedUserByToken(token.token).observe(context) { userRoom->
            user = userRoom
            userName = user.firstName + " " + user.lastName
        }
    }

    Spacer(Modifier.size(15.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_profile_colorier),
            modifier = Modifier.size(72.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "Profile picture description"
        )
    }

    Spacer(Modifier.size(10.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = userName)
    }

    Column(modifier = modifier.fillMaxSize()) {

        Spacer(Modifier.size(15.dp))

        Divider(color = MaterialTheme.colorScheme.onPrimary)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            val checkedState = remember { mutableStateOf(true) }
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Red,
                    checkedTrackColor = Color.Green,
                    uncheckedThumbColor = Color.Gray
                )
            )
        }

        Divider(color = MaterialTheme.colorScheme.onPrimary)

        DrawerButton(
            icon = painterResource(id = R.drawable.baseline_home_24),
            label = stringResource(id = R.string.home_app),
            isSelected = currentRoute == WazzabyDrawerDestinations.HOME_ROUTE,
            action = {
                scope.launch {
                    drawerState.close()
                    if (currentRoute == WazzabyDrawerDestinations.HOME_ROUTE) {
                        viewItem.value =  ConstValue.publicMessage
                    }
                }


                navController.navigate(WazzabyDrawerDestinations.HOME_ROUTE)
            }
        )

        DrawerButton(
            icon = painterResource(id = R.drawable.baseline_subject_24),
            label = stringResource(id = R.string.problematic_app),
            isSelected = currentRoute == WazzabyDrawerDestinations.PROBLEM_ROUTE,
            action = {
                scope.launch {
                    drawerState.close()
                    if (currentRoute == WazzabyDrawerDestinations.PROBLEM_ROUTE) {
                        viewItem.value = ConstValue.problem
                    }
                }

                navController.navigate(WazzabyDrawerDestinations.PROBLEM_ROUTE)
            }
        )
    }
}

@Composable
@ExperimentalMaterial3Api
private fun DrawerButton(
    icon: Painter,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val textIconColor = if (isSelected) {
        colors.primary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val backgroundColor = if (isSelected) {
        colors.primary.copy(alpha = 0.12f)
    } else {
        Color.Transparent
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavigationIcon(
                    icon = icon,
                    isSelected = isSelected,
                    contentDescription = null,
                    tintColor = textIconColor
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textIconColor
                )
            }
        }
    }
}