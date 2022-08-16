package com.android.wazzabysama.ui.views.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.wazzabysama.R
import com.android.wazzabysama.ui.theme.WazzabySamaTheme
import com.android.wazzabysama.ui.views.bottomnavigationviews.PublicMessageView
import com.android.wazzabysama.ui.views.shimmer.PublicMessageShimmer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun chips(
    paddingValues: PaddingValues,
    listState: LazyListState
) {
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

        items(8) { problematic ->
            OutlinedButton(
                border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary/*colorResource(R.color.Purple700)*/),
                modifier = Modifier.padding(2.dp),
                onClick = {   }) {
                Icon(
                    imageVector = Icons.Outlined.Psychology,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.inscription), color = MaterialTheme.colorScheme.primary)
            }
        }

    }
}