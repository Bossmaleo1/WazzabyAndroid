package com.android.wazzabysama.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.android.wazzabysama.BuildConfig
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.UIEvent.Event.AuthEvent
import com.android.wazzabysama.ui.components.NavigationIcon
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import com.android.wazzabysama.ui.views.bottomnavigationviews.getOurPublicMessageImage
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
    userViewModel: UserViewModel
) {
    var userName by rememberSaveable { mutableStateOf("") }
    userViewModel.getSavedToken()
    val screenState = userViewModel.screenState.value

    userViewModel.onEvent(AuthEvent.GetSavedToken)
    //We test is the token exist
    if (screenState.tokenRoom.isNotEmpty() && screenState.tokenRoom[0] !== null) {
        userViewModel.onEvent(AuthEvent.GetSavedUserByToken)
    }


    Spacer(Modifier.size(15.dp))
    if (screenState.userRoom.isNotEmpty() && screenState.userRoom[0] !== null) {
        userViewModel.onEvent(AuthEvent.GetSavedProblematic)
        userName = screenState.userRoom[0].firstName + " " + screenState.userRoom[0].lastName
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Content that needs to appear/disappear goes here:
            if (screenState.userRoom[0].imageUrl?.length == 0) {
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_colorier),
                    modifier = Modifier.size(72.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Profile picture description"
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${screenState.userRoom[0].imageUrl}"),
                    modifier = Modifier
                        .padding(4.dp)
                        .height(100.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(50.dp))),
                    contentDescription = "Profile picture description"
                )
            }
        }
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
        ) {
            val checkedState = remember { mutableStateOf(true) }
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Red,
                    checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                    uncheckedThumbColor = Color.Gray
                )
            )
        }

        Divider(color = MaterialTheme.colorScheme.onPrimary)

        DrawerButton(
            icon = Icons.Outlined.Home,
            label = stringResource(id = R.string.home_app),
            isSelected = currentRoute == WazzabyDrawerDestinations.HOME_ROUTE,
            action = {
                scope.launch {
                    drawerState.close()
                    if (currentRoute == WazzabyDrawerDestinations.HOME_ROUTE) {
                        viewItem.value = ConstValue.publicMessage
                    }
                }
                navController.navigate(WazzabyDrawerDestinations.HOME_ROUTE)
            }
        )

        DrawerButton(
            icon = Icons.Outlined.Psychology,
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
    icon: ImageVector,
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