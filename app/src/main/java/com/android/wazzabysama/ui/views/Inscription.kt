package com.android.wazzabysama.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.wazzabysama.R

@Composable
@ExperimentalMaterial3Api
fun FormStepFirstView(navController: NavHostController) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TextField(
            value = email,
            onValueChange = {
                email = it
                isError = when {
                    email.text.count() < 5 -> {
                        true
                    } else -> {
                        false
                    }
                }
            },
            label = { Text(stringResource(id = R.string.your_email)) },
            placeholder = { Text("") },
            leadingIcon = {
                IconButton(onClick = {  }) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "", tint = colorResource(
                        R.color.Purple700
                    ))
                }
            },
            isError = isError,
            modifier = Modifier
                .padding(0.dp,60.dp,0.dp,0.dp)
        )

        if (isError) {
            Row(modifier = Modifier.fillMaxWidth()
                                   .padding(30.dp,0.dp,0.dp,0.dp),
                horizontalArrangement = Arrangement.Start){
                Text(
                    text = "Error message",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Divider(color = colorResource(R.color.Purple700),
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
                painter = painterResource(id = R.drawable.baseline_east_24),
                "contentDescription",
                tint = colorResource(R.color.Purple700)
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