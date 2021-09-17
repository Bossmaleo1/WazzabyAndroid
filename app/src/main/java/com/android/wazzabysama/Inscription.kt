package com.android.wazzabysama

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FormStepFirstView(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        var email by rememberSaveable { mutableStateOf("") }

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
            modifier = Modifier
                .padding(0.dp,60.dp,0.dp,0.dp)
        )

        Divider(color = colorResource(R.color.colorPrimary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End) {

        IconButton(onClick = { }){
            Icon(
                painter = painterResource(id = R.drawable.outline_arrow_forward_black_24),
                "contentDescription",
                tint = colorResource(R.color.colorPrimary)
            )
        }

    }
}

@Composable
fun FormStepSecondView(navController: NavHostController) {

}

@Composable
fun FormStepDoneView(navController: NavHostController) {

}