package com.android.wazzabysama.ui.views.bottomnavigationviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.wazzabysama.R
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

@ExperimentalMaterial3Api
@Composable
fun PublicMessageView() {
    val userName by rememberSaveable { mutableStateOf("Sidney MALEO") }
    val postTime by rememberSaveable { mutableStateOf("Il y a 2 jours") }
    val content by rememberSaveable { mutableStateOf("Lorem Ipsum is simply dummy text of " +
            "the printing and typesetting industry. Lorem Ipsum has been the industry's " +
            "standard dummy text ever since the 1500s, when an unknown printer " +
            "took a galley of type and scrambled it to make a type specimen book. " +
            "It has survived not only five centuries, but also the leap into electronic " +
            "typesetting, remaining essentially unchanged. It was popularised in the 1960s" +
            " with the release of Letraset sheets containing Lorem Ipsum passages, and more " +
            "recently with desktop publishing software like Aldus PageMaker including " +
            "versions of Lorem Ipsum") }
    val countLike by rememberSaveable { mutableStateOf("0") }
    val countDisLike by rememberSaveable { mutableStateOf("0") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
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
                    text = userName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = postTime,
                    modifier = Modifier.padding(4.dp, 0.dp,0.dp,0.dp),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = content ,
                modifier = Modifier.padding(10.dp, 0.dp,0.dp,0.dp),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {

            Image(
                painter = painterResource(id = R.drawable.photo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

        }

        Divider(color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)) {
                Row(modifier = Modifier
                    .wrapContentWidth()) {
                    Icon(
                        Icons.Filled.AddComment,
                        contentDescription = null
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("0 ")
                    Text(stringResource(id = R.string.talk),  style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }

                Row(modifier = Modifier
                    .wrapContentWidth()) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("0 ")
                }

                Row(modifier = Modifier
                    .wrapContentWidth()) {
                    var expanded by remember { mutableStateOf(false) }
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDownCircle,
                            contentDescription = "Localized description"
                        )
                    }

                    //we create our Dropdown Menu Item
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Modifier ce post") },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Edit,
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text("Supprimer ce post") },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null
                                )
                            })

                        DropdownMenuItem(
                            text = { Text("Signaler ce post") },
                            onClick = { /* Handle send feedback! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Flag,
                                    contentDescription = null
                                )
                            })
                    }
                }

            }

        }

    }
}