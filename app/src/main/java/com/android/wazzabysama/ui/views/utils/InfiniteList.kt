package com.android.wazzabysama.ui.views.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.android.wazzabysama.R
import com.android.wazzabysama.data.model.data.Problematic
import com.android.wazzabysama.data.model.data.PublicMessage
import com.android.wazzabysama.data.model.data.Token
import com.android.wazzabysama.presentation.viewModel.publicMessage.PublicMessageViewModel
import com.android.wazzabysama.ui.views.bottomnavigationviews.PublicMessageView
import com.android.wazzabysama.ui.views.shimmer.PublicMessageShimmer
import kotlinx.coroutines.flow.distinctUntilChanged

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfiniteListMessagePublicRemote(
    listState: LazyListState,
    listItems: List<PublicMessage>,
    paddingValues: PaddingValues,
    publicMessageViewModel: PublicMessageViewModel,
    problematic: Problematic,
    token: String,

    //onLoadMore: () -> Unit
) {

    LazyColumn(
        contentPadding = paddingValues, //PaddingValues(),
        state = listState
    ) {

        items(listItems) { publicMessage ->
            PublicMessageView(publicMessage)
        }

        items(count = 1) {
            PublicMessageShimmer()
        }
    }

    /*InfiniteListHandler(listState = listState) {
        onLoadMore()
    }*/
    listState.OnBottomReached(buffer = 2) {
        publicMessageViewModel.getPublicMessage(problematic,publicMessageViewModel.currentPage.value + 1,token)
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
