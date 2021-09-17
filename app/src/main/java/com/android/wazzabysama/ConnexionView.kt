package com.android.wazzabysama

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun Connexion(navController: NavHostController) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden  by rememberSaveable { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.wazzabysama),
                contentDescription = "The Application Launcher",
                modifier = Modifier
                    .padding(0.dp, 30.dp, 0.dp, 0.dp)
                    .height(100.dp)
                    .width(100.dp)
            )
        }

        Divider(color = colorResource(R.color.colorPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp))

        TextField(
            value = email,
            onValueChange = {email = it },
            label = { Text(stringResource(id = R.string.your_email)) },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "", tint = colorResource(R.color.colorPrimary))
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
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Filled.Lock, contentDescription = "", tint = colorResource(R.color.colorPrimary))
                }
            },
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) painterResource(id = R.drawable.outline_visibility_24)
                        else painterResource(id = R.drawable.outline_visibility_off_24)
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
            Text(stringResource(R.string.connexion))
        }

        Divider(color = colorResource(R.color.colorPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp))

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start) {
        ClickableText(
            buildAnnotatedString {
                pushStringAnnotation(tag = "",
                    annotation = "")
                withStyle(style = SpanStyle(color =  colorResource(R.color.colorPrimary),
                    fontWeight = FontWeight.Bold, textDecoration=TextDecoration.Underline,
                    fontSize = 15.sp
                )
                ) {
                    append(stringResource(id = R.string.password_forget))
                }

                pop()
            },
            onClick = {

            })
    }

    Column(
    modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {
        ClickableText(
            buildAnnotatedString {
                pushStringAnnotation(tag = "",
                    annotation = "")
                withStyle(style = SpanStyle(color =  colorResource(R.color.colorPrimary),
                    fontWeight = FontWeight.Bold, textDecoration=TextDecoration.Underline,
                    fontSize = 15.sp
                )
                ) {
                    append(stringResource(R.string.inscription))
                }

                pop()
            },
            onClick = {
                navController.navigate("inscription_step_first")
            })
    }

}

