package com.android.wazzabysama

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource


@Composable
fun Connexion() {
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
                painter = painterResource(id = R.drawable.wazzabysama) ,
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
                        if (passwordHidden) Icons.Filled.Check else Icons.Filled.Done
                    // Please provide localized description for accessibility services
                    val description = if (passwordHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
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
                    /* Do something! */
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
        Text(text = stringResource(id = R.string.password_forget),
            color = colorResource(R.color.colorPrimary))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {
        Text(text = stringResource(R.string.inscription),
             color = colorResource(R.color.colorPrimary))
    }

}