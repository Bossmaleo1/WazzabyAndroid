package com.android.wazzabysama.ui.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.android.wazzabysama.R
import com.android.wazzabysama.presentation.viewModel.user.UserViewModel
import com.android.wazzabysama.ui.UIEvent.Event.AuthEvent
import com.android.wazzabysama.ui.UIEvent.UIEvent
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations
import kotlinx.coroutines.flow.collectLatest


@Composable
@ExperimentalMaterial3Api
fun Login(navController: NavHostController, userViewModel: UserViewModel, context: Any) {
    var email by rememberSaveable { mutableStateOf("sidneymaleoregis@gmail.com") }
    var password by rememberSaveable { mutableStateOf("Nfkol3324012020@!") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val screenState = userViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()

    Column {
        if (screenState.isLoad) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                },
                title = {

                },
                text = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row {
                            CircularProgressIndicator()
                            Row(Modifier.padding(10.dp)) {
                                Text(text = stringResource(id = R.string.wait))
                            }
                        }

                    }
                },
                confirmButton = {

                },
                dismissButton = {

                }
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {

            OutlinedButton(
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                onClick = { navController.navigate("inscription_step_first") }) {
                Icon(
                    imageVector = Icons.Outlined.ManageAccounts,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    stringResource(id = R.string.inscription),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.wazzabysama),
                contentDescription = "The Application Launcher",
                modifier = Modifier
                    .padding(0.dp, 10.dp, 0.dp, 0.dp)
                    .height(75.dp)
                    .width(75.dp)
            )
        }


        OutlinedTextField(
            value = email,
            modifier = Modifier.padding(top = 10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
            ),
            onValueChange = {
                email = it
                userViewModel.onEvent(
                    AuthEvent.EmailValueEntered(it)
                )
            },
            label = { Text(stringResource(id = R.string.your_email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        OutlinedTextField(
            value = password,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            ),
            onValueChange = {
                password = it
                userViewModel.onEvent(
                    AuthEvent.PasswordValueEntered(it)
                )
            },
            label = { Text(stringResource(id = R.string.your_password)) },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Outlined.Visibility
                        else Icons.Outlined.VisibilityOff
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(
                        imageVector = visibilityIcon,
                        contentDescription = description,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },

            modifier = Modifier
                .padding(top = 30.dp)
        )

        OutlinedButton(
            modifier = Modifier
                .width(280.dp)
                .padding(top = 30.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
            onClick = {
                //we init our fields
                userViewModel.onEvent(
                    AuthEvent.IsInitField(email, password)
                )
                //we test if fields is Empties
                userViewModel.onEvent(
                    AuthEvent.IsEmptyField
                )

                //We initialize the connection parameters
                userViewModel.onEvent(
                    AuthEvent.ConnectionAction
                )

                //We get our token
                userViewModel.onEvent(
                    AuthEvent.GetToken(
                        userName = email,
                        password = password
                    )
                )
            }) {
            Icon(
                imageVector = Icons.Outlined.Login,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.connexion))
        }

        if (screenState.isNetworkError) {
            userViewModel.onEvent(AuthEvent.IsNetworkError)
        } else if (!screenState.isNetworkConnected) {
            userViewModel.onEvent(AuthEvent.IsNetworkConnected)
        }

        LaunchedEffect(key1 = true) {
            userViewModel.uiEventFlow.collectLatest {event->
                when(event) {
                    is UIEvent.ShowMessage-> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
        }

        LaunchedEffect(key1 = screenState.user.isNotEmpty() && screenState.token.isNotEmpty()) {
            if (screenState.user.isNotEmpty() && screenState.token.isNotEmpty()) {
                navController.navigate(WazzabyDrawerDestinations.HOME)
            }
        }

    }


}