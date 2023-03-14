package com.android.wazzabysama.ui.views.bottomnavigationviews

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.wazzabysama.BuildConfig
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.PublicMessage
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun getOurPublicMessageImage(publicMessage: PublicMessage): Painter {
    if (publicMessage.user.images.isEmpty()) {
       return  painterResource(id = R.drawable.ic_profile)
    }
    return  rememberAsyncImagePainter("${BuildConfig.BASE_URL_DEV}/images/${publicMessage.user.images[publicMessage.user.images.size - 1].imageName}")
}

@RequiresApi(Build.VERSION_CODES.O)
@ExperimentalMaterial3Api
@Composable
fun PublicMessageViewItem(publicMessage: PublicMessage) {
    val formatter: SimpleDateFormat = SimpleDateFormat("EEE d MMM yy", Locale.getDefault())
    val published = formatter.format(publicMessage.published)

    val userName by rememberSaveable { mutableStateOf("${publicMessage.user.firstName} ${publicMessage.user.lastName}") }
    val postTime by rememberSaveable { mutableStateOf(published) }
    val content by rememberSaveable { mutableStateOf(publicMessage.content) }
    val countCommentaries by rememberSaveable { mutableStateOf("${publicMessage.comments.size}") }
    var expandedContentText by remember { mutableStateOf(true)}
    var expandedButtonVisibility by remember { mutableStateOf(false) }
    var expandedIntermediate by remember {mutableStateOf(true)}
    var visibleImage by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(2.5.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {

        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {

            AnimatedVisibility(
                visible = visibleImage,
                enter = fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                // Content that needs to appear/disappear goes here:
                Image(
                    painter = getOurPublicMessageImage(publicMessage),
                    contentDescription = "Profile picture description",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .height(50.dp)
                        .width(50.dp)
                        .clip(RoundedCornerShape(corner = CornerSize(25.dp)))
                )
            }

            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }
                )
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_access_time_black_24),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(18.dp)
                            .width(18.dp),
                        colorFilter = ColorFilter.tint(color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  })

                    )

                    Text(
                        text = postTime,
                        modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                        style = MaterialTheme.typography.titleSmall,
                        color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }
                    )
                }

            }
        }

        Row(
            modifier = Modifier
                .padding(start = 5.dp, top = 0.dp, end = 5.dp, bottom = 0.dp)
                .animateContentSize(),
            horizontalArrangement = Arrangement.Center
        ) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = content,
                    modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 0.dp),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Justify,
                    color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                    overflow = TextOverflow.Ellipsis,
                    maxLines =  if (expandedContentText) Int.MAX_VALUE else 3,
                    onTextLayout = {textLayoutResult: TextLayoutResult ->
                        if (expandedIntermediate) {
                            if (textLayoutResult.lineCount > 3) {
                                expandedButtonVisibility = true
                                expandedContentText = false
                            }
                            expandedIntermediate = false
                        }
                    }
                )
            }
        }
        if (expandedButtonVisibility) {
            Row(
                modifier = Modifier
                    .padding(0.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = {  expandedContentText = !expandedContentText }) {
                    Icon(
                        imageVector = if(expandedContentText) Icons.Outlined.KeyboardDoubleArrowUp else Icons.Outlined.KeyboardDoubleArrowDown,
                        contentDescription = null,
                        tint = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = if(expandedContentText) stringResource(id = R.string.read_less) else stringResource(id = R.string.read_more), color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  })
                }
            }
        }

        if (publicMessage.images.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .background(colorResource(R.color.gray_400))
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Start,
            ) {
                AnimatedVisibility(
                    visible = visibleImage,
                    enter = fadeIn(
                        // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                        initialAlpha = 0.4f
                    ),
                    exit = fadeOut(
                        // Overwrites the default animation with tween
                        animationSpec = tween(durationMillis = 250)
                    )
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = "${BuildConfig.BASE_URL_DEV}/images/${publicMessage.images[publicMessage.images.size - 1].imageName}",
                            placeholder = painterResource(id = R.drawable.baseline_panorama_white_48),
                            error = painterResource(id = R.drawable.baseline_error_white_48)
                        ),
                        contentDescription = "Profile picture description",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize(),
                    )
                }
            }
        }

        Divider(
            color = if(!isDark) { MaterialTheme.colorScheme.onPrimary } else { Color.White},
            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
        )

        Row(
            modifier = Modifier
                .padding(5.dp, 0.dp, 0.dp, 10.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    Icon(
                        Icons.Outlined.QuestionAnswer,
                        tint = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                        contentDescription = null
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(countCommentaries, color =if (!isDark) { colorResource(R.color.black40) } else { Color.White  })
                    Text(
                        " ${stringResource(id = R.string.talk)}",
                        style = MaterialTheme.typography.titleMedium, color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        tint = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                        contentDescription = null,
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text("0 ", color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  })
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
                            tint = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                            contentDescription = "Localized description"
                        )
                    }

                    //we create our Dropdown Menu Item
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.edit_post), color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }) },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Edit,
                                    tint = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                                    contentDescription = null
                                )
                            })
                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.delete_post), color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    tint =if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                                    contentDescription = null
                                )
                            })

                        DropdownMenuItem(
                            text = { Text(stringResource(id = R.string.report_post), color = if (!isDark) { colorResource(R.color.black40) } else { Color.White  }) },
                            onClick = { /* Handle send feedback! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Flag,
                                    tint = if (!isDark) { colorResource(R.color.black40) } else { Color.White  },
                                    contentDescription = null
                                )
                            })
                    }
                }
            }
        }
    }

    LaunchedEffect(true) {
        delay(3)
        visibleImage = true
    }
}