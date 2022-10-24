package com.android.wazzabysama.ui.views.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class ChipData(var label: String, var check: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chips(
    paddingValues: PaddingValues,
    listState: LazyListState
) {
    var selectDefault = "TEST MALEO 3"
    val chipsData = SnapshotStateList<ChipData>()
    chipsData.add(ChipData("TEST MALEO 1", "TEST MALEO 1" === selectDefault))
    chipsData.add(ChipData("TEST MALEO 2", "TEST MALEO 2" === selectDefault))
    chipsData.add(ChipData("TEST MALEO 3", "TEST MALEO 3" === selectDefault))
    chipsData.add(ChipData("TEST MALEO 4", "TEST MALEO 4" === selectDefault))
    chipsData.add(ChipData("TEST MALEO 5", "TEST MALEO 5" === selectDefault))
    chipsData.add(ChipData("TEST MALEO 6", "TEST MALEO 6" === selectDefault))


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
                )
            }
        }

        itemsIndexed(chipsData) { index, problematic ->
            Row(
                modifier = Modifier.padding( end = 2.dp, start = 2.dp)
            ) {
                AssistChip(
                    onClick = {
                        val indexTarget = chipsData.indexOf(ChipData(selectDefault, true))
                        chipsData[indexTarget] = ChipData(selectDefault, false)
                        chipsData[index] = ChipData(chipsData[index].label, true)
                        selectDefault = chipsData[index].label
                    },
                    border = if (problematic.check) AssistChipDefaults.assistChipBorder(
                        disabledBorderColor = Color.Red,
                        borderWidth = 1.dp,
                        borderColor = MaterialTheme.colorScheme.primary
                    ) else AssistChipDefaults.assistChipBorder(),
                    label = {
                        Text(
                            text = problematic.label,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                            color = if (problematic.check) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Psychology,
                            tint = if (problematic.check) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Localized description",
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
        }
    }

}