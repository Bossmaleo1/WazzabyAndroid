package com.android.wazzabysama.ui.views.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.ui.UIEvent.UIEvent
import com.android.wazzabysama.ui.views.bottomnavigationviews.PublicMessageViewItem
import com.android.wazzabysama.ui.views.shimmer.PublicMessageShimmer
import com.android.wazzabysama.ui.views.viewsError.networkError
import kotlinx.coroutines.flow.collectLatest
import com.android.wazzabysama.R

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfiniteListMessagePublicRemote(
    listState: LazyListState,
    paddingValues: PaddingValues,
    publicMessageViewModel: PublicMessageViewModel,
    problematic: Problematic,
    token: String,
) {

    val screenState = publicMessageViewModel.screenState.value
    val scaffoldState = rememberScaffoldState()

    LazyColumn(
        contentPadding = paddingValues,
        state = listState
    ) {

        items(screenState.publicMessageList) { publicMessage ->
            PublicMessageViewItem(publicMessage)
        }

        if (screenState.isLoad) {
            items(count = 1) {
                PublicMessageShimmer()
            }
        }

        if (!screenState.isNetworkConnected) {
            items(count = 1) {
                networkError(
                    title = stringResource(R.string.network_error),
                    iconValue = 0
                )
            }
        } else if (screenState.isNetworkError) {
            items(count = 1) {
                networkError(
                    title = stringResource(R.string.is_connect_error),
                    iconValue = 1
                )
            }
        }


    }



    listState.OnBottomReached(buffer = 2) {
        publicMessageViewModel.getPublicMessage(
            problematic = problematic,
            token = token
        )
    }

}

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    loadMore : () -> Unit
){
    // Buffer must be positive.
    // Or our list will never reach the bottom.
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    // state object which tells us if we should load more
    val shouldLoadMore = remember {
        derivedStateOf {
            // get last visible item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true
            // Check if last visible item is the last item in the list
            lastVisibleItem.index == layoutInfo.totalItemsCount - 1 - buffer
        }
    }
    
    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}
