package com.android.wazzabysama.ui.views.bottomnavigationviews

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.wazzabysama.R

@ExperimentalMaterial3Api
@Composable
fun PrivateMessageView() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable{
                      Log.d("Bossmaleo191919","MALEO-SAMA !!")
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
                painter = painterResource(id = R.drawable.ic_profile_colorier),
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
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Et maintenant il parle lui même de venir chez ...",
                    modifier = Modifier.padding(4.dp, 0.dp,0.dp,0.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        Divider(color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(10.dp))
    }

}