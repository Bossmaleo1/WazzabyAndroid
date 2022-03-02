package com.android.wazzabysama.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.android.wazzabysama.R


@Composable
fun Connexion(navController: NavHostController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
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
                navController.navigate("home")
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
                    append(stringResource(id = R.string.password_forget))
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

