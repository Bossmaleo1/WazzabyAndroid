package com.android.wazzabysama.ui.views.bottomnavigationviews

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.wazzabysama.R
import com.android.wazzabysama.ui.components.WazzabyDrawerDestinations

@ExperimentalMaterial3Api
@Composable
fun PrivateMessageView(navController: NavHostController) {
    val isDark = isSystemInDarkTheme()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable{
                navController.navigate(WazzabyDrawerDestinations.CONVERSATION)
            },
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(4.dp)
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(25.dp)))
            )
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = "Sidney MALEO",
                    modifier = Modifier.padding(4.dp, 0.dp,0.dp,0.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                    maxLines = 1
                )
                Text(
                    text = "Et maintenant il parle lui même de venir chez maleo-sama regis maleo",
                    modifier = Modifier.padding(4.dp, 0.dp,0.dp,0.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                    maxLines = 1
                )
            }
        }

    }
    Divider(color = if(!isDark) { MaterialTheme.colorScheme.onPrimary } else { Color.White})

}

