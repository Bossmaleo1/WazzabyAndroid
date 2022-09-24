package com.android.wazzabysama.ui.views.utils

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.wazzabysama.data.model.data.PublicMessage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chips(
    paddingValues: PaddingValues,
    listState: LazyListState
) {
    var selectedTemp by remember { mutableStateOf( "TEST MALEO 3") }
    var testMaleo = mutableStateListOf(
        "TEST MALEO 1",
        "TEST MALEO 2",
        "TEST MALEO 3",
        "TEST MALEO 4",
        "TEST MALEO 5",
        "TEST MALEO 6"
    )


    LazyRow(
        contentPadding = paddingValues,
        state = listState
    ) {
        items(1) {
            Row(
                modifier = Modifier.padding(top = 12.dp, end = 2.dp, start = 10.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Icon(
                    imageVector = Icons.Outlined.History,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        items(testMaleo) { problematic ->
            var selectedIndex by remember { mutableStateOf(testMaleo.indexOfFirst { it == selectedTemp }) }
            Row(
                modifier = Modifier.clickable {
                    //selectedIndex = 2
                    selectedTemp = "TEST MALEO 1"
                    Log.d("TestMALEO","MALEO-SAMA MALEO-SAMA MALEO-SAMA")
                }
            ) {
                /*ChoiceChipContent(
                    problematic,
                    2,
                    testMaleo,
                    selected = remember { problematic == selectedTemp },
                    modifier = Modifier.padding(end = 10.dp, top = 5.dp, bottom = 5.dp)
                )*/
                var isSelected by remember { mutableStateOf(problematic == selectedTemp) }

                Surface(
                    color = when {
                        isSelected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                        else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f)
                    },
                    contentColor = when {
                        isSelected -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.padding(end = 10.dp, top = 5.dp, bottom = 5.dp),
                    border = BorderStroke(
                        1.dp,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Row(
                        modifier = Modifier.clickable {

                            //isSelected
                            selectedTemp = "TEST MALEO 1"
                            Log.d("TestMALEO","MALEO-SAMA MALEO-SAMA MALEO-SAMA")
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Outlined.Psychology,
                            contentDescription = null,
                            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 2.dp, vertical = 8.dp)
                        )

                        Text(
                            text = problematic,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }

                }
            }
        }

    }
}

@Composable
private fun ChoiceChipContent(
    text: String,
    indexOf: Int,
    listarray: SnapshotStateList<String>,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    var isSelected by remember { mutableStateOf(selected) }

    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.04f)
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
        border = BorderStroke(
            1.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )
    ) {
        Row(
            modifier = Modifier.clickable {

                //isSelected
                //selectedTemp = "TEST MALEO 1"
                Log.d("TestMALEO","MALEO-SAMA MALEO-SAMA MALEO-SAMA")
            }
        ) {

            Icon(
                imageVector = Icons.Outlined.Psychology,
                contentDescription = null,
                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 2.dp, vertical = 8.dp)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }

    }
}