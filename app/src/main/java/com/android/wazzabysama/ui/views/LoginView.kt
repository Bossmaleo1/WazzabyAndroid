package com.android.wazzabysama.ui.views

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.android.wazzabysama.R
import com.android.wazzabysama.R.string.password_forget
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.User
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.model.dataRoom.TokenRoom
import com.android.wazzabysama.data.model.dataRoom.UserRoom
import com.android.wazzabysama.data.util.Resource
import com.android.wazzabysama.presentation.util.Event
import com.android.wazzabysama.presentation.viewModel.UserViewModel
import com.android.wazzabysama.presentation.viewModel.UserViewModelFactory
import javax.inject.Inject


@Composable
@ExperimentalMaterial3Api
fun Login(navController: NavHostController, userViewModel: UserViewModel, context: Any) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val isLoading = remember { mutableStateOf(false) }

    fun showProgressBar(){
        isLoading.value = true
    }

    fun hideProgressBar(){
        isLoading.value = false
    }

    Column {
        if (isLoading.value) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    isLoading.value = false
                },
                title = {

                },
                text = {
                    Column( modifier = Modifier
                        .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Row() {
                            CircularProgressIndicator()
                            Row(Modifier.padding(10.dp)) {
                                Text(text = "Connexion en cours...")
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

    fun getUser(userViewModel: UserViewModel, userName: String, token: String, context: Any) {
        userViewModel.getUser(userName, token)
        userViewModel.user.observe(context as LifecycleOwner) {user->
            when (user) {
                is Resource.Success -> {
                    Log.d("Test1", "'user':'${user.data?.Users?.get(0)?.lastName}'");
                    val user = user.data?.Users?.get(0) as User
                    val problematic = user.problematic as Problematic
                    //we save the user Token
                    userViewModel.saveToken(
                        TokenRoom(
                            1,
                            //we split the bear characters
                            token.split(" ")[1])
                    )
                    //We save the user Problematic
                    userViewModel.saveProblematic(ProblematicRoom(
                        problematic.id,
                        problematic.wording,
                        problematic.language,
                        problematic.icon
                    ))

                    //We save the user
                    userViewModel.saveUser(UserRoom(
                        user.id,
                        user.online,
                        user.anonymous,
                        problematic.id,
                        user.email,
                        user.firstName,
                        user.lastName,
                        user.images[0].imageName,
                        user.pushNotifications?.get(0)?.keyPush,
                        user.roles[0],
                        user.username,
                        //we split the bear characters
                        token.split(" ")[1]
                    ))
                    hideProgressBar()
                    navController.navigate("home")
                }

                is Resource.Error -> {
                    hideProgressBar()
                    user.message?.let {
                        Toast.makeText(context as Context, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    fun viewModelLogin(userViewModel: UserViewModel, userName: String, password: String, context: Any) {
        userViewModel.getToken(userName, password)
        userViewModel.token.observe(context as LifecycleOwner) {token->
            when (token) {
                is Resource.Success -> {
                    Log.d("Test1", "'token':'${token.data?.token}'");
                    token.data?.token?.let {
                        getUser(userViewModel, userName,
                            "Bearer $it",context as LifecycleOwner)
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    token.message?.let {
                        Toast.makeText(context as Context, "An error occurred : $it", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.wazzabysama),
                contentDescription = "The Application Launcher",
                modifier = Modifier
                    .padding(0.dp, 30.dp, 0.dp, 0.dp)
                    .height(100.dp)
                    .width(100.dp)
            )
        }

        Divider(
            color = colorResource(R.color.Purple700),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp)
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(id = R.string.your_email)) },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(id = R.string.your_password)) },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "",
                        tint = colorResource(R.color.Purple700)
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) painterResource(id = R.drawable.baseline_visibility_24)
                        else painterResource(id = R.drawable.baseline_visibility_off_24)
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(painter = visibilityIcon, contentDescription = description)
                }
            },

            modifier = Modifier
                .padding(top = 30.dp)
        )

        Button(
            modifier = Modifier
                .width(280.dp)
                .padding(top = 30.dp),
            onClick = {
                //navController.navigate("home")
               // Toast.makeText(context,"MALEO MALEO MALEO",Toast.LENGTH_LONG).show()
                viewModelLogin(userViewModel, "sidneymaleoregis@gmail.com","Nfkol3324012020@!", context)
               // Log.d("Test1", "Here");
            }) {
            Text(stringResource(R.string.connexion), color = Color.White)
        }

        Spacer(Modifier.size(20.dp))

        ClickableText(
            buildAnnotatedString {
                pushStringAnnotation(
                    tag = "",
                    annotation = ""
                )
                withStyle(
                    style = SpanStyle(
                        color = colorResource(R.color.Purple700),
                        fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline,
                        fontSize = 15.sp
                    )
                ) {
                   // append(stringResource(id = password_forget))
                }

                pop()
            },
            onClick = {

            })

        Divider(
            color = colorResource(R.color.Purple700),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        Button(onClick = {  navController.navigate("inscription_step_first") }) {
            Icon(
                painterResource(id = R.drawable.baseline_question_answer_24),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(id = R.string.inscription), color = Color.White)
        }

    }

}