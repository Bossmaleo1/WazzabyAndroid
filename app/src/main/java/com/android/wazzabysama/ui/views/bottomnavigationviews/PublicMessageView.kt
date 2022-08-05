package com.android.wazzabysama.ui.views.bottomnavigationviews

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.wazzabysama.BuildConfig
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.PublicMessage
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun PublicMessageView(publicMessage: PublicMessage) {
    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
    val published = formatter.format(publicMessage.published)

    val userName by rememberSaveable { mutableStateOf("${publicMessage.user.firstName} ${publicMessage.user.lastName}") }
    val postTime by rememberSaveable { mutableStateOf(published) }
    val content by rememberSaveable { mutableStateOf(publicMessage.content) }
    val countCommentaries by rememberSaveable { mutableStateOf("${publicMessage.comments.size}") }
    val countDisLike by rememberSaveable { mutableStateOf("0") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.5.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
    ) {

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            if (publicMessage.user.images.isEmpty()) {
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
            } else {
                Image(
                    painter = rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${publicMessage.user.images[publicMessage.user.images.size - 1].imageName}"),
                    modifier = Modifier
                        .padding(4.dp)
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(25.dp))),
                    contentDescription = "Profile picture description"
                )
            }

            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(R.color.black40)
                )
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_access_time_black_24),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(R.color.black40))

                    )

                    Text(
                        text = postTime,
                        modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                        style = MaterialTheme.typography.titleSmall,
                        color = colorResource(R.color.black40)
                    )
                }

            }
        }

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = content,
                modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(R.color.black40)
            )
        }

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {

            if (publicMessage.images.isNotEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.photo),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Image(
                    painter = rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${publicMessage.images[publicMessage.images.size - 1].imageName}"),
                    modifier = Modifier
                        .padding(4.dp)
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(25.dp))),
                    contentDescription = "Profile picture description"
                )
            }
        }


        Divider(
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(10.dp)
        )

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    Icon(
                        Icons.Outlined.QuestionAnswer,
                        tint = colorResource(R.color.black40),
                        contentDescription = null
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(countCommentaries, color = colorResource(R.color.black40))
                    Text(
                        " ${stringResource(id = R.string.talk)}",
                        style = MaterialTheme.typography.titleMedium, color = colorResource(R.color.black40)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        tint = colorResource(R.color.black40),
                        contentDescription = null,
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("0 ", color = colorResource(R.color.black40))
                }

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    var expanded by remember { mutableStateOf(false) }
                    // RowScope here, so these icons will be placed horizontally
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowDropDownCircle,
                            tint = colorResource(R.color.black40),
                            contentDescription = "Localized description"
                        )
                    }

                    //we create our Dropdown Menu Item
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.edit_post), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Edit,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.delete_post), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })

                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.report_post), color = colorResource(R.color.black40)) },
                            onClick = { /* Handle send feedback! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Flag,
                                    tint = colorResource(R.color.black40),
                                    contentDescription = null
                                )
                            })
                    }
                }

            }

        }

    }
}